/**************************************************************************
* 2012, U.S. Government, National Security Agency, National Information Assurance Research Laboratory
*
* This is a work of the UNITED STATES GOVERNMENT and is not subject to copyright
* protection in the United States. Foreign copyrights may apply.
*
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
*
* ...Redistributions of source code must retain the above copyright notice,
*   this list of conditions and the following disclaimer.
*
* ...Redistributions in binary form must reproduce the above copyright notice,
*   this list of conditions and the following disclaimer in the documentation
*   and/or other materials provided with the distribution.
*
* ...Neither the name of the NATIONAL SECURITY AGENCY/NATIONAL INFORMATION ASSURANCE RESEARCH LABORATORY
*   nor the names of its contributors may be used to endorse or promote products
*   derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
* IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
* INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
* OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
* OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
* OF THE POSSIBILITY OF SUCH DAMAGE.
**************************************************************************/


package gov.niarl.his;

import gov.niarl.sal.webservices.hisWebService.client.*;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.Action;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.ActionDelay;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisPollingWebService;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.HisWebService;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.NonceSelect;
import gov.niarl.sal.webservices.hisWebServices.clientWsImport.Quote;
import java.util.Properties;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.apache.log4j.PropertyConfigurator;
import org.trustedcomputinggroup.xml.schema.core_integrity_v1_0_1_.ComponentIDType;
import org.trustedcomputinggroup.xml.schema.core_integrity_v1_0_1_.ObjectFactory;
import org.trustedcomputinggroup.xml.schema.core_integrity_v1_0_1_.ValueType;
import org.trustedcomputinggroup.xml.schema.core_integrity_v1_0_1_.VendorIdType;
import org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.PcrCompositeType;
import org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.PcrSelectionType;
import org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.QuoteDataType;
import org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.QuoteInfoType;
import org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.QuoteSignatureType;
import org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.QuoteType;
import org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.ReportType;
import org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.SnapshotType;
import org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.PcrCompositeType.PcrValue;
import org.w3._2000._09.xmldsig_.KeyInfoType;
import org.w3._2000._09.xmldsig_.KeyValueType;
import org.w3._2000._09.xmldsig_.SignatureMethodType;
import org.w3._2000._09.xmldsig_.SignatureValueType;






/** StandaloneHIS is a self contained client application for the Host Integrity at Startup (HIS) system.
 * This application can be run as any other and will query the local Trusted Platform Module (TPM) for
 * host integrity measurements in the form of a signed Quote.<br>
 * <br>
 * The HIS Client makes use of binary TPM Interface Module to access the TPM via a system call,
 * a TCG defined XML integrity report format and web service stype communications with a back end HIS server.<br>
 * <br>
 * The HIS Client can be run in a once through mode that runs the reporting process and then terminates or
 * an On Demand mode that sets the process to poll for instructions via web services.
 * The On Demand mode has expanded the range of functions that the HIS Client can carry out
 * including capabilities for system reboot (in order to generate fresh measurements) and process
 * specially configured commands in order to handle scenarios with virtualized guest environments.<br>
 * <br>
 * The HIS Client application uses log4j logging as well as sending special Error Reports to the back end server.
 * In addition to this certain errors also print to Std Out or Std Err to better enable field debugging.
 * <br>
 * The HIS Client also makes use of a HIS.properties file located in the root of the application path supplied as an argument.
 * This property file contains all of the Client's configuration data.  While defaults can be substituted lack of a
 * proper configuration will cause the HIS Client to not function.
 * <br>
 * Other dependencies include both a binary TPM interface module and splash screen image, the location of which are pointed to in the HIS.properties file.
 * <br>
 * 
 *
 * @author mcbrotz
 */
public class StandaloneHIS 
{
    
    //Version for the UUID
    public static String UUID_VERSION = "1";
    //Prefix to differentiate the snapshot ID space
    public static final String SNAPSHOT_PREFIX = "S";
    //Prefix to differentiate the snapshot ID space
    public static final String QUOTE_PREFIX = "Q";       
    //TCG Required Quote Version Major Value - placeholder
    public static final short QUOTE_VERSION_MAJOR = 0x01;
    //TCG Required Quote Version Minor Value - placeholder
    public static final short QUOTE_VERSION_MINOR = 0x01;    
    //TCG Required Quote Version Rev Major Value - placeholder
    public static final short QUOTE_VERSION_REV_MAJOR = 0x00;
    //TCG Required Quote Version Rev Minor Value - placeholder
    public static final short QUOTE_VERSION_REV_MINOR = 0x00;    
    //Fimename for auto-reg cert file
    //public static final String AIC_FILNAME = "AIK.cer";
    //Snapshot Revision Number
    public static final String SNAPSHOT_REV_LEVEL = "0";  
    //size of the PCR bitmask in bytes
    //TODO make this a property?
    //public static final int PCR_BITMASK_SIZE = 3;
    //variable for the PCR bitmask
    private byte[] pcrBitmask;
    //default value for the pcr bitmask
    public static final byte[] DEFAULT_PCR_BITMASK = {(byte)0xff, (byte)0xff, (byte)0xff};
    //Default blocking timeout value in whole seconds.
    public static final String DEFAULT_TIMEOUT_VALUE = "10";

    //command flags for the native interface
    public static final String MODE_FLAG = "-mode";
    public static final String NONCE_FLAG = "-nonce";
    public static final String BITMASK_FLAG = "-mask";    
    public static final String KEY_AUTH_FLAG = "-key_auth";
    public static final String KEY_INDEX_FLAG = "-key_index";

    //Properties file object for the application
    Properties hisProperties = new Properties();

    //The timeout in approximate ms for any blocking function to wait for a process to return
    int blockingTimeout=10000;
    int splashDuration = 3000;
    
    //The nonce for the TPM quote
    String nonce="";
    //This is the raw bitmask as sent by the web service
    String rawBitmask="";
    //global String for the applications working directory
    String hisPath="./";
    //The type of appraiser registration to use, manual or auto
    //String regType = "manual";
    //the flag for the running OS
    int hostOS=0;
    //host name of the local computer
    String computerName= "unknownHost";

    //name for the log4j properties file
    public static final String LOG4J_PROPERTIES_FILE = "log4j.properties";

    public static Logger s_logger;
    
    //Web Service variables
    HisWebService hisAuthenticationWebService;
    String webServiceUrl = "";
    
    //Property labels and defaults
    public static final String TPM_QUOTE_EXECUTABLE_NAME_LABEL = "TpmQuoteExecutableName";
    public static final String TPM_QUOTE_EXECUTABLE_PATH_LABEL = "TpmQuoteExecutablePath";
    public static final String VENDOR_GUID_LABEL = "VendorGUID";
    public static final String VENDOR_NAME_LABEL = "Vendor";
    public static final String MODEL_NAME_LABEL = "ModelName";
    public static final String MODEL_NUMBER_LABEL = "ModelNumber";
    public static final String VERSION_MAJOR_LABEL = "ModelMajorRev";
    public static final String VERSION_MINOR_LABEL = "ModelMinorRev";
    public static final String WEB_SERVICE_URL_LABEL = "WebServiceUrl";
    public static final String MODEL_SN_LABEL = "ModelSerialNumber";
    public static final String MODEL_PATCH_LEVEL_LABEL = "PatchLevel";
    public static final String MODEL_MFG_DATE_LABEL = "MfgDate";
    public static final String BLOCKING_TIMEOUT_LABEL = "BlockingTimeout";
    public static final String KEY_AUTH_LABEL = "KeyAuth";
    public static final String KEY_INDEX_LABEL = "KeyIndex";
    public static final String TRUST_STORE_LABEL = "TrustStore";
    public static final String SPLASH_IMAGE_LABEL = "SplashImage";
    public static final String POLLING_PERIOD_LABEL = "PollingPeriod";
    public static final String UUID_VERSION_LABEL = "UUIDversion";
    //public static final String REGISTRATION_TYPE_LABEL = "RegistrationType";
    public static final String OS_TYPE_LABEL = "OSType";
    public static final String VERIFY_COMMAND_LABEL = "VerifyClientAction";
    public static final String CLEAN_COMMAND_LABEL = "CleanClientAction";

    //OS Type ID
    static final int WINDOWS_OS = 1;
    static final int LINUX_OS = 2;
    public static final String WINDOWS_OS_TAG ="W";
    public static final String LINUX_OS_TAG ="X";

    //Various Constants
    public static final String DEFAULT_HIS_PATH = "/OAT/";
    public static final String PROPERTIES_NAME = "OAT.properties";    
    public static final String PROPERTIES_EXTENSION = ".properties";
    public static final String DEFAULT_KEY_AUTH = "0123456789012345678901234567890123456789";
    public static final String DEFAULT_KEY_INDEX = "1";
    public static final String DEFAULT_POLLING_VALUE = "30";
    public static final String TPM_QUOTE_MODE = "5";
    public static final String TPM_QUOTE1_MODE = "56";
    public static final String TPM_QUOTE2_MODE = "24";
    public static final String ERROR_MESSAGE_ID = "0001";
    public static final String UNLOCK_SCREEN_MESSAGE_ID = "0002";
    public static final String WINDOWS_REBOOT_CMD = "shutdown -r";
    public static final String LINUX_REBOOT_CMD = "shutdown -r 15";

    //global variable that indicates the quote type to be used
    int quoteType=2;
    
    //TPM Quote Struct Constants
    public static final String EXPECTED_QUOTE_VERSION_TAG = "01010000";
    public static final String QUOTE_FIXED = "QUOT";//TCG Defined Quote Info Fxied Value
    //public static final int SEGMENT_LENGH_FIELD_SIZE = 4;
    public static final int PCR_FIELD_SIZE = 2;
    public static final int PCR_LENGTH_SIZE = 2;
    public static final int PCR_SIZE = 20;
    public static final int QUOTE_SIZE = 48;
    public static final int QUOTE_VERSION_SIZE = 4;
    public static final int QUOTE_FIXED_SIZE = 4;
    public static final int PCR_HASH_SIZE = 20;
    public static final int NONCE_SIZE = 20;
    public static final int SIGNATURE_SIZE = 256;
    //TPM Quote 2 Struct Constants
    public static final int QUOTE2_SIZE_BASE = 49;//not including variable length bitmask
    public static final byte[] EXPECTED_QUOTE2_VERSION_TAG = {(byte)0x00, (byte)0x36};
    public static final String QUOTE2_FIXED = "QUT2"; //TCG Defined Quote 2 Info Fxied Value
    public static final String QUOTE2_FILLER_BYTE = "01";
    public static final int QUOTE2_VERSION_SIZE = 2;
    public static final int QUOTE2_BITMASK_LENGTH_SIZE = 2;


    String tpmOutput = "";
    

            /**
             * Removes white space from a string.
             * @param string String from which white space will be removed.
             * @return String without white space.
             */
            public static String removeWhiteSpace(String string) {
                    string = string.replace("\r", "");
                    string = string.replace("\n", "");
                    string = string.replace("\t", "");
                    string = string.replace(" ", "");
                    return string;
            }

            /**
             * Turns a hexadecimal string representation to a byte array.
             * @param string Hexadecimal formatted string.
             * @return Byte array generated from a hexadecimal string representation
             */
            public static byte[] unHexString(String string) {

                    string = removeWhiteSpace(string);
                    string = string.toUpperCase().replace("0X", "");

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    for (int i = 0; i < string.length(); i += 2) {
                            byteArrayOutputStream.write(Integer.parseInt(string.substring(i, i + 2), 16));
                    }

                    return byteArrayOutputStream.toByteArray();
            }

            /**
             * Turn a byte array into a hexadecimal string representation.
             * @param byteArray Byte array to be converted into a hexadecimal
             * string representation.
             * @return A string containing a hexadecimal representation of a byte
             * array without space or 0x's.
             */
            public static String hexString(byte[] byteArray) {
                    String returnstring = new String();

                    for (int i = 0; i < byteArray.length; i++) {
                            Integer integer = byteArray[i] < 0 ? byteArray[i] + 256 : byteArray[i];
                            String integerString = Integer.toString(integer, 16);
                            returnstring += integerString.length() == 1 ? "0" + integerString : integerString;
                    }

                    return returnstring.toUpperCase();
            }

            

    /** Main medhod for running the application.<br>
     *<br>
     * @param args the command line arguments<br>
     *<br>
     * The first parameter is the working directory.
     *<br>
     * The second is the flasg to handle the splash screen:<br>
     * -s if the splash screen is to be displayed<br>
     * -b if the splash screen is to be displayed without running the TPM report<br>
     * -d if the HIS client is to be run in dynamic mode (disables splash screen)<br>
     *<br>
     * The parameters can be used individually, but only one of each<br>
     */
    public static void main(String[] args) 
    {
        String path=DEFAULT_HIS_PATH;
        StandaloneHIS his=null;
        boolean dynamicHIS = false;
        boolean showSplash = false;
        boolean brandHIS = false;

        //bounds check for presence of the first argument
        if(args.length>=1)
        {
            //check for the presence of a second argument, path first
            if(args.length>=2 && (args[1].equals("-s")||args[1].equals("-b")||args[1].equals("-d")))
            {
                if(args[1].equals("-s"))
                {
                    showSplash = true;
                    path = args[0];
                }
                if(args[1].equals("-b"))
                {
                    brandHIS=true;
                    path = args[0];
                }
                if(args[1].equals("-d"))
                {
                    dynamicHIS = true;
                    path = args[0];
                }
            }
            //check for the presence of a second argument, path second just in case the user screws up
            else if(args.length>=2 && (args[0].equals("-s")||args[0].equals("-b")||args[0].equals("-d")))
            {
                if(args[0].equals("-s"))
                {
                    showSplash = true;
                    path = args[1];
                }
                if(args[0].equals("-b"))
                {
                    brandHIS=true;
                    path = args[1];
                }
                if(args[0].equals("-d"))
                {
                    dynamicHIS = true;
                    path = args[1];
                }

            }
            else if(args[0].equals("-d"))
            {
                dynamicHIS=true;
            }
            else if(args[0].equals("-b"))
            {
                brandHIS=true;
            }
            else if(args[0].equals("-s"))
            {
                showSplash = true;
            }
            else
            {
                path = args[0];
            }

        }

        System.out.println("Flags recieved: Brand = "+brandHIS+", Show Splash = "+showSplash+", On Demand = "+dynamicHIS+", Path = "+path);

        try
        {
            his = new StandaloneHIS(path, showSplash, brandHIS);
        }
        catch(Exception e)
        {
             e.printStackTrace();
             System.exit(1);
        }

        //run the integrity check once
        try
        {
            his.checkIntegrity();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.exit(1);
        }

        //if we are setting up dynamic polling do it
        //If entered this function will not return.
        if(dynamicHIS)
        {
            his.dynamicPoll();
        }
        
        
    }
    
    /** Constructor.  Loads HIS property file, various properties and other global variables
     *
     * @param path The path to where the HIS files have been installed
     * @param splash Is the splash scteen to be displayed
     * @param brand Is HIS in branding mode
     * @throws java.lang.Exception Thown is HIS has a problem
     */
    
    public StandaloneHIS(String path, boolean splash, boolean brand) throws Exception
    {
        
        if(path.length() == 0)
        {
            path = DEFAULT_HIS_PATH;
        }

        hisPath = path;

        //set up the logging.
        PropertyConfigurator.configure(hisPath+LOG4J_PROPERTIES_FILE);
        s_logger = Logger.getLogger( "HIS" );


        //Set up the HIS properties file.
        FileInputStream HISPropertyFile = null;
        
        try
        { 
            HISPropertyFile = new FileInputStream(hisPath+PROPERTIES_NAME);
        }
        catch (java.io.FileNotFoundException fnfe)
        {
        	 s_logger.warn( "HIS Property file not found!" );
            //Try to create the Property File if it is not present
            try
            {
                File fileName= new File(hisPath+PROPERTIES_NAME);  // create the directory
                fileName.createNewFile();
                HISPropertyFile = new FileInputStream(fileName);
                s_logger.debug( "Using HIS Property File: "+ HISPropertyFile.toString());
                
            }
            catch (Exception ioe)
            {
                s_logger.error("HIS Property File empty.\n Unable to create new HIS property file!\n");
            }        
        }
        
        
        //If we load in a file put it in to a proprties object
        if(HISPropertyFile!=null)
        {
            try
            {
                hisProperties.load(HISPropertyFile);

            }
            catch (java.io.FileNotFoundException fnfe)
            {
                s_logger.error( "HIS Property file not found on Property load!" );
            }
            catch (java.io.IOException ioe)
            {
                s_logger.error( "Error loading HIS Property file!" );
            }  
            finally{
            	HISPropertyFile.close();
            }
        }
        else
        {
            s_logger.error( "Error loading HIS Property file!" );
        }
        
        //Set various properties
        blockingTimeout = 1000 * new Integer(hisProperties.getProperty(BLOCKING_TIMEOUT_LABEL, DEFAULT_TIMEOUT_VALUE)).intValue();
        
        //Set up the Web services
                
        //Initialize the SSL Trust store
        String trustStoreUrl = hisPath+hisProperties.getProperty(TRUST_STORE_LABEL,"./TrustStore.jks");
        System.setProperty("javax.net.ssl.trustStore", trustStoreUrl);
        
        //Pull the URLs from properties
        webServiceUrl = hisProperties.getProperty(WEB_SERVICE_URL_LABEL,"");

        //Set the UUID version number
        UUID_VERSION = hisProperties.getProperty(UUID_VERSION_LABEL, "1");

        //get the registration type
        //regType = hisProperties.getProperty(REGISTRATION_TYPE_LABEL, "manual");

        //Set the web service URL
        webServiceUrl = hisProperties.getProperty(WEB_SERVICE_URL_LABEL,"");

        //check for valid URLs.
        if(webServiceUrl.length() == 0)
        {
            throw new Exception("Web Service URL not present. Unable to initialize HIS client.");
        }

        //get the OS type
        String hostOSstr = hisProperties.getProperty(OS_TYPE_LABEL, "");

        if(hostOSstr.equals(WINDOWS_OS_TAG))
        {
            hostOS = WINDOWS_OS;
        }
        else if(hostOSstr.equals(LINUX_OS_TAG))
        {
            hostOS = LINUX_OS ;
        }
        else{hostOS=0;}

        //Obtain the computer name
        try
        {
            computerName = InetAddress.getLocalHost().getCanonicalHostName();
            s_logger.debug("Computer name found as: "+computerName);
        }

        catch(Exception ex)
        {
        	//computerName="DefaultHost";
            //s_logger.warn("Computer name set to default");
        	
        	//UnknownHostException will be thrown sometime on RHEL,
        	//so get computer name from the exception msg
    		StringTokenizer st = new StringTokenizer(ex.getMessage());
    		while (st.hasMoreTokens()) computerName = st.nextToken();
        }       

        //pull the splash image from properties
        String splashFile = hisProperties.getProperty(SPLASH_IMAGE_LABEL);

        System.out.println("Splash path = "+hisPath + splashFile);

        //only display a splash screen if a splash image file has been entered and the flag is set
        if(splashFile!=null && (splash == true || brand == true))
        {
            new HisSplash(splashDuration, hisPath + splashFile).showSplash();
            //if we are in Branding mode show the splash screen and exit
            if(brand == true)
            {
                s_logger.info( "HIS branding display complete" );
                Thread.sleep(splashDuration+100);
                System.exit(0);
            }
        }


    }

    /** This function sets up the HIS application into a dynamic polling mode where the app will poll a web service to determine if it needs to send back a report.
     *
     * The poll interval is set within the property file and defaults to 30 seconds.
     *
     *
     */

    public void dynamicPoll()
    {
        HisPollingWebService hisPollingWebService = null;
        ActionDelay actionDelay = null;
        Action action = null;
        long pollInterval;
        int defaultPollInterval;
        
        //load the default polling interval value in whole seconds
        defaultPollInterval = new Integer(hisProperties.getProperty(POLLING_PERIOD_LABEL, DEFAULT_POLLING_VALUE)).intValue();

        //enter a polling loop
        while(true)
        {
            pollInterval = 0;
            action = Action.DO_NOTHING;


            try 
            {
                //make a call to the web service to get the type of action and action delay
                hisPollingWebService = HisWebServicesClientInvoker.getHisPollingWebService(webServiceUrl);//"http://toc.dod.mil:8080/HisWebServices");
				if(hisPollingWebService==null)
                {
                    System.out.println("Web service object null");
                }
				
				else{
					actionDelay = hisPollingWebService.getNextAction(computerName);

					action = actionDelay.getAction();

	                //save the delay until the next poll
	                pollInterval = actionDelay.getDelayMilliseconds();
				}

            } 
            catch (Exception e) 
            {
                //we want to trigger the default polling interval if there is a web service exception
                pollInterval=0;

                //Handle the exception by reporting the error, but do not kill the loop
                s_logger.error( "On Demand web service Error: "+e.getMessage() );
                System.out.println( "On Demand web service Error: "+e.getMessage() );
                //e.printStackTrace();
            }
            
			switch (action) 
            {
				case DO_NOTHING:
					System.out.println("Action:DO_NOTHING");
					 s_logger.debug("Action:DO_NOTHING");
					break;
				case SEND_REPORT:
					System.out.println("Action:SEND_REPORT");
                    s_logger.debug("Action:SEND_REPORT");
                    try
                    {
                        checkIntegrity();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        s_logger.error( "Error checking integrity on demand: "+e.getMessage() );
                    }
					break;
				case REBOOT:
					System.out.println("Action:REBOOT");
                    s_logger.debug("Action:REBOOT");
                    //TODO ENABLE THIS WHEN SAFE
                    restartComputer(hostOS);
					break;
                case VERIFY_CLIENT:
                    System.out.println("Action: VERIFY CLIENT");
                    s_logger.debug("Action: VERIFY CLIENT");
                    
                    if (actionDelay != null)
                    	commandProcessor(VERIFY_COMMAND_LABEL, actionDelay.getArgs() == null? "":actionDelay.getArgs());
                    break;
                    
                case CLEAN_CLIENT:
                    System.out.println("Action: CLEAN CLIENT");
                    s_logger.debug("Action: CLEAN CLIENT");
                    if (actionDelay != null)
                    	commandProcessor(CLEAN_COMMAND_LABEL, actionDelay.getArgs() == null? "":actionDelay.getArgs());
                    break;
			}

            //delay for the poll interval
            try
            {
                if(pollInterval==0)
                {
                    Thread.sleep(defaultPollInterval*1000);
                }
                else
                {
                	if (actionDelay != null)
                		Thread.sleep(actionDelay.getDelayMilliseconds());
                }
                
            }
            //do we care about this o.0
            catch(InterruptedException e)
            {
            	e.printStackTrace();
            }
        }

    }

    /** Method performs a system integrity check by calling the TPM and returning the Report.
     *
     * @throws java.lang.Exception
     */
    
    public void checkIntegrity() throws Exception
    {
        s_logger.debug( "Checking system integrity" );
        
        int bitCount=0; //number of PCR values asked for in the bitmask

        String userName;
        String tpmInput="";
        byte[] b = new byte[4];
        ReportType report;
        SnapshotType snap;
        QuoteDataType quote;
        String errMsg="*";
        String quoteMode = TPM_QUOTE_MODE;

        //get the key Auth and Index properties
        String keyAuth  = hisProperties.getProperty(KEY_AUTH_LABEL, DEFAULT_KEY_AUTH);
        String keyIndex = hisProperties.getProperty(KEY_INDEX_LABEL, DEFAULT_KEY_INDEX);
         s_logger.debug("Using Key Auth " + keyAuth  + " and Key Index " + keyIndex + ".\n");      

        //Get the username
        userName = System.getProperty("user.name");
        s_logger.debug("User name found as: "+userName);

        //Initialize the web service
        hisAuthenticationWebService = HisWebServicesClientInvoker.getHisWebService(webServiceUrl);      
                
        //this sets the nonce and bitmask global variables via a web service call
        try
        {
            getReportParams(userName, computerName);
            
        }
        //Failure to get proper params will result in an error report
        catch(Exception e)
        {
            s_logger.error("Error recieving server Nonce and Bitmask.");

            report = createEmptyReport("Error recieving server Nonce and Bitmask: "+ e.getMessage(), ERROR_MESSAGE_ID);

            sendIntegrityReport(report);

            throw new Exception( "Error recieving server Nonce and Bitmask. " +  e.getMessage());

        }
        
        //log the nonce and bitmask
        s_logger.debug("Nonce recieved: "+nonce); 
        s_logger.debug("Bitmask recieved: "+rawBitmask);

        //convert the raw bitmask to one that we can use
        try
        {
            pcrBitmask  = unHexString(rawBitmask);  
        }
        //if there is a problem, use default
        catch(Exception e)
        {
            pcrBitmask  = DEFAULT_PCR_BITMASK;  
            s_logger.error("Error recieving PCR Bitmask.  Using default value."); 
        }

        //if the bitmask is invalid, use default
        if(pcrBitmask.length>3 || pcrBitmask.length<1)
        {
            pcrBitmask  = DEFAULT_PCR_BITMASK;
            s_logger.error("Error recieving PCR Bitmask.  Using default value."); 
        }

        //select the TPM QUOTE Mode (v1 or v2) based on the parameter from the web service (formerly bitmask length)
        if(quoteType == 2)//pcrBitmask.length == 3)// quoteType == 2)
        {
            //quoteType = 2;
            quoteMode = TPM_QUOTE2_MODE;
        }
        else if(quoteType == 1)//pcrBitmask.length == 2)
        {
            //quoteType = 1;
            quoteMode = TPM_QUOTE_MODE;
        }
        else
        {
            //quoteType = 1;
            quoteMode = TPM_QUOTE_MODE;
        }

        //construct the input string
        tpmInput = MODE_FLAG + " " + quoteMode + " " + BITMASK_FLAG + " "+ rawBitmask + " " + NONCE_FLAG +
                " " + nonce + " " + KEY_AUTH_FLAG + " " + keyAuth + " " + KEY_INDEX_FLAG + " " + keyIndex;
                
        //make the TPM call that gets the required PCR values.  The complete return is stored in the member variable tpmOutput
        errMsg = runTPMrequest(tpmInput);
        
        //If we don't get a responce handle the error by creating an empty report and sending that back
//        if(!errMsg.equals(""))
        if (errMsg.length() != 0)
        {
            report = createEmptyReport(errMsg, ERROR_MESSAGE_ID);

            sendIntegrityReport(report);
      
            throw new Exception( "Error retrieving TPM data! " +  errMsg);
        }     
        
        //count all the bits in the mask
        //Made more complicated by Java's lack of support for bits
        for(int i = 0; i<pcrBitmask.length; i++)
        {
            //s_logger.debug("Byte = "+pcrBitmask[i]); 
            b[3]=pcrBitmask[i];
            BigInteger bi = new BigInteger(b);
            int bitInt = bi.intValue();
            bitCount = bitCount+Integer.bitCount(bitInt);
        }
        
        s_logger.debug("Bit count = "+bitCount);

        //create the base integrity report
        report = createIntegrityReport(computerName);

        //Set up the system snapshot object and add it to the integrity report
        snap = createSnapshot(report.getID(), "");
        report.getSnapshotCollection().add(snap);
        
        //parse the Quote and add it to the integrity report
        try
        {
            quote = parseQuote(quoteType, bitCount, report.getID());
            report.getQuoteData().add(quote);            
        }
        catch(Exception e)
        {
            report = createEmptyReport(errMsg + " Invalid TPM Data: "+e.getMessage(), ERROR_MESSAGE_ID);
            throw new Exception(e.getMessage());
        }

        //if everything works out send the report onto the server
        try
        {
            sendIntegrityReport(report);
        }      
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        
    }

    /** Invokes the auto-registration process that sends the AIC to the back end if the back end has indicated that it lacks it
     *
     */

    /*
    private void registerHost() throws Exception
    {

        FileInputStream fin;
        X509Certificate aic;

        try
        {
            fin = new FileInputStream(hisPath + AIC_FILNAME);
        }
        catch (FileNotFoundException fnfe)
        {
            throw new Exception("AIC cert file not found");
        }
        try
        {
            aic = X509Certificate.getInstance(fin);
            //TODO call the web service to send the AIC to the apraiser
        }
        catch (CertificateException ce)
        {
            s_logger.error("Unable to load AIC: " + ce.getMessage());
            throw ce;
        }

        //TODO call the web service to send the AIC to the apraiser


    }
     * */
    
    
    /** Makes a runtime call to the TPM executable interface and captures its output
     * 
     * @param tpmInput The input string to the TPM Quote function
     */
    
    private String runTPMrequest(String tpmInput)
    {
        //set up the call to the TPM executable interface
        int i = 0 ;
        int exitVal=99999;
        int j = 0;
        int dataTimeout = blockingTimeout /10;        
        StreamPrinter sp=null;
        StreamOutput so=null;
        Runtime rt = Runtime.getRuntime();
        String tpmInterfacePath = hisProperties.getProperty(TPM_QUOTE_EXECUTABLE_PATH_LABEL, hisPath);
        String tpmInterfaceName = hisProperties.getProperty(TPM_QUOTE_EXECUTABLE_NAME_LABEL, "NIARL_TPM_Module.exe");
        
        this.tpmOutput = "";
        
        try
        {
            //Run the process
            Process proc = rt.exec(tpmInterfacePath+tpmInterfaceName + " " + tpmInput);
            
            //make sure to grab any error messages
            sp = new StreamPrinter(proc.getErrorStream(), "ERROR", this);
            
            //and grab the input from the process for the integrity report
            so = new StreamOutput(proc.getInputStream(), this);
            
            sp.start();
            so.start();
            
            //now loop until we get a return value or we reach a timeout
            while(i<=blockingTimeout)
            {

                try
                {
                    exitVal = proc.exitValue();
                }
                catch(Exception e)
                {
                    //if the process has not completed we get an exception, just catch and loop
                    Thread.sleep(1);
                }
                if(exitVal!=99999)
                {
                    //if we get a valid exit code break out of the loop
                    break;
                }
                
                i++;
            }
            
            //if i has reached the blocking timeout value we need to throw an error
            if(i>=blockingTimeout)
            {
                //kill the threads
                sp.interrupt();
                so.interrupt();
                //kill the process
                proc.destroy();
                so.stopThread();
                sp.stopThread();                
                
                System.out.println("TPM Quote interface timeout, process aborted.  Unable to capture quote.");
                s_logger.error("TPM Quote interface timeout, process aborted.  Unable to capture quote.");
                return "TPM Interface Timeout";
            }
            
            //if we get a non-0 return value then we also have a problem
            if(exitVal!=0)
            {
                System.out.println("TPM Quote interface returned error code "+exitVal+".");
                s_logger.error("TPM Quote interface returned error code "+exitVal+".");
                return "TPM error: "+exitVal;
            }
            
            //Provide time for the output to be read from StdOut
            while(j<=dataTimeout )
            {
                if (this.tpmOutput.length() < 1)
                    Thread.sleep(1);// give a litte extra time before stopping the other threads so Output can be updated
                else break;
                j++;
            }
            
            //if j has reached the data wait timeout
            if(j>=dataTimeout )
            {                  
                //kill the process
                proc.destroy();
                //kill the threads
                sp.interrupt();
                so.interrupt();
                so.stopThread();
                sp.stopThread();       
          
                //throw new IOException("TPM interface data wait timeout, process aborted.  Unable to capture TPM data.");
                s_logger.error("TPM interface data wait timeout, process aborted.  Unable to capture TPM data.");
                return "TPM Data Timeout";
            }              
            
            so.stopThread();
            sp.stopThread();            

        }
        catch(Throwable t)
        {
            t.printStackTrace();

            System.out.println("Error running TPM Quote interface!  Unable to capture quote.");
            
            s_logger.error("Error running TPM Quote interface!  Unable to capture quote.", t);
            return "TPM Interface Unavailable: "+t.getMessage();
        }
        
                
        return "";

    }    
    
    /** Generates the base XML integrity report accouting to the TCG Integrity Report 1.0 Scheema
     * Report needs to have the Quote and Snapshot data added to it later
     *
     * @param hostName The hostname of the machine
     *
     */
    
    private ReportType createIntegrityReport(String hostName)
    {
        ReportType report = new ReportType();
        String reportID;
        
        //Set the Report ID to the host name concatonated with the time ++
        reportID=hostName+"-"+System.currentTimeMillis();       
        report.setID(reportID);
        
        //Set the UUID to the UUID prefix concatonated with the reportID ++
        report.setUUID(generateUUID(UUID_VERSION));

        
        return report;
        
    }

    /** Creates an empty report with no quote structure.  This is used in case there is an issue accessing the TPM or no TPM is installed.
     *  @param message a message proviging the reason for the empty report
     * @param messageTag a code setting the class of message sent in the Empty report
     *
     * @return the empty integrity report with message in the snapshot
     */

    private ReportType createEmptyReport(String message, String messageTag)
    {
        ReportType report;
        SnapshotType snap;
        String taggedMessage;

        //combine the message and the ID to avoid problems using the any type
        taggedMessage = messageTag + "-" + message;

        //create the base integrity report
        report = createIntegrityReport(computerName);

        //Set up the system snapshot with the error message
        snap = createSnapshot(report.getID(), taggedMessage);
        report.getSnapshotCollection().add(snap);

        return report;

    }
    
    /** This creates the system snapshot object.  The snapshot can contain a listing of all the system components and their various attributes
     * Only one component is required along with various snapshot ID.  Each component element itself have a large list of optional data that pertains solely to that component.
     *
     * This method is currently only filling in the single required component element with default values.
     *
     * Also used for the error report by stuffing the error report into the Value Type field.
     *
     * The PcrHash must be added seperately.
     *
     * @param id The ID number for the overall integrity report.
     * @param taggedMessage A message string tagged with an appropiate value for use in Error reports or to send other information to the server
     */
    
    private SnapshotType createSnapshot(String id, String taggedMessage)
    {
        SnapshotType snap  = new SnapshotType();
        ComponentIDType component = new ComponentIDType();
        VendorIdType vendorID = new VendorIdType();
        JAXBElement<String> vendorGUID = new ObjectFactory().createVendorIdTypeVendorGUID(hisProperties.getProperty(VENDOR_GUID_LABEL, "0000"));
        ValueType messageValue = new ValueType();//Error message storage
        KeyValueType kv = new KeyValueType();//Error message storage
        org.w3._2000._09.xmldsig_.ObjectFactory of = new org.w3._2000._09.xmldsig_.ObjectFactory();

        //Get the Vendor GUID and other info from a from a property file
        vendorID.getTcgVendorIdOrSmiVendorIdOrVendorGUID().add(vendorGUID);
        vendorID.setName(hisProperties.getProperty(VENDOR_NAME_LABEL, "Unknown Vendor")); 

        
        //construct the component type
        component.setVendorID(vendorID);
        component.setId("Default_Component");
        //optional data fields, use TBD
        component.setModelName(hisProperties.getProperty(MODEL_NAME_LABEL, "")); 
        component.setModelNumber(hisProperties.getProperty(MODEL_NUMBER_LABEL, "XXXX")); 
        component.setVersionMajor(new BigInteger(hisProperties.getProperty(VERSION_MAJOR_LABEL, "00"))); 
        component.setVersionMinor(new BigInteger(hisProperties.getProperty(VERSION_MINOR_LABEL, "00")));        
        component.setModelSerialNumber(hisProperties.getProperty(MODEL_SN_LABEL, "XXXX")); 
        component.setPatchLevel(hisProperties.getProperty(MODEL_PATCH_LEVEL_LABEL, ""));
        
        //construct the Composite Hash Type
        //pcrHashType.setId(id);
        //pcrHashType.setValue(pcrHash);
             
        //Now construct the snapshot object                
        //Set the IDs
        snap.setUUID(generateUUID(UUID_VERSION));//UUID_PREFIX + "."+SNAPSHOT_PREFIX+"."+id);
        snap.setId(SNAPSHOT_PREFIX+"."+id);
        snap.setRevLevel(new BigInteger(SNAPSHOT_REV_LEVEL));
        //add the component
        snap.setComponentID(component);
        //Add the pcr hash returned from the TPM quote  into a digest value
        
        //snap.getCompositeHash().add(pcrHash);

        //if a message is set add it to a Value type to be reported to the server
//        if(!taggedMessage.equals(""))
        if (taggedMessage.length() != 0)
        {
            //construct the ValueType with the message
            //To match the schema we must add a Key Value object with a placeholder value
            kv.getContent().add("-");
            messageValue.setAny(of.createKeyName("placeholder"));
            messageValue.setId(taggedMessage);//the message is stored in the Value type ID field
            snap.getValues().add(messageValue);
        }
        
        return snap;
    }
    
    /** Takes raw PCR data and constructs a QuoteData element that conrails the a PCR value quote and a corresponding signature.  
     * @param id The report ID to for use by the Quote Data object.
     * @param pcrComposite This object is loaded with all of the PCR values from the TPM
     * @param quoteSignature The object containing the TPM signature over the returned PCR data
     * @param nonce The nonce returned with the TPM quote
     * @param pcrHash a hash of all the returned PCR values which is signed in the quote
     */
    private QuoteDataType createQuoteDataEntry(String reportId, PcrCompositeType pcrComposite, QuoteSignatureType quoteSignature, byte[] nonce, byte[] pcrHash, short quoteVersion)
    {
        
        //Here is the element we return
        QuoteDataType qData = new QuoteDataType();
        
        //set the ID field
        qData.setID(QUOTE_PREFIX+"."+reportId);
        
        //QuoteData needs these two other objects
        QuoteType quote = new QuoteType();
        QuoteSignatureType tpmSIG =quoteSignature;
        
        //Quote needs the following objects, PcrComposite and QuoteInfo
        quote.setPcrComposite(pcrComposite);
        QuoteInfoType quoteInfo = new QuoteInfoType();
          

        //Now set up the Quote Info first with various pre-configured values
        quoteInfo.setVersionMajor(QUOTE_VERSION_MAJOR);
        quoteInfo.setVersionMinor(QUOTE_VERSION_MINOR);   
        quoteInfo.setVersionRevMajor(QUOTE_VERSION_REV_MAJOR);
        quoteInfo.setVersionRevMinor(QUOTE_VERSION_REV_MINOR);
        if(quoteVersion==1)
        {
            quoteInfo.setFixed(QUOTE_FIXED);
        }
        if(quoteVersion==2)
        {
            quoteInfo.setFixed(QUOTE2_FIXED);
        }

        //then the nonce and pcr hash
        quoteInfo.setExternalData(nonce);
        quoteInfo.setDigestValue(pcrHash);


        quote.setQuoteInfo(quoteInfo);
        
        //add the components to the quote data type
        qData.setQuote(quote);
        qData.setTpmSignature(tpmSIG);
        
        
        return qData;
    }
    

    /** Parses a raw TPM version 1 quote (in Hex String format) and puts the PCR values into an TCG Integrity report
     *
     *  This function does not attempt to check if the Quote values are valid
     *
     * @param quoteVer The version of quote to parse against, Quote or Quote 2
     * @param pcrNumber The number of PCR values being returned
     * @param reportID The Report ID.
     * @return A Quate Data object containing the data parsed from the TPM output
     * @throws java.io.IOException
     */
    
    private QuoteDataType parseQuote(int quoteVer, int pcrNumber, String reportID) throws IOException
    {
        //All HexString sizes are BYTES TIMES TWO
        int segmentSize=0;
        int sizeCounter = 0;
        String quoteVersionTag= "";
        byte[] bitmask = pcrBitmask;
        String bitmaskLenStr= "";
        int bitmaskLen=1;
        String returnedNonce="";
        byte[] nonceBytes;
        String pcrHash="";
        byte[] pcrHashBytes;
        String signature="";
        PcrCompositeType pcrComposite;
        PcrSelectionType pcrSelect = new PcrSelectionType();
         QuoteSignatureType quoteSig = new QuoteSignatureType();
        QuoteDataType quote;

        //This parcer scans through the output of the TPM module and rigorously checks to make sure the returned value meet the spec

        
        //---------------------READ PCR VALUES-------------------------------//
        
        s_logger.debug("Parsing "+pcrNumber+" PCR values.");
                
        pcrComposite = new PcrCompositeType();        
        
        //pull the PCR list from the snapshot to add the values to
        List pcrs = pcrComposite.getPcrValue();
        
        //Loop through the PCR values adding them to the list
        for(int i = 0; i<pcrNumber; i++)
        {
            PcrValue pcrEntry = new org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.ObjectFactory().createPcrCompositeTypePcrValue();
            String pcrValue;
            byte[] pcrValueBytes;
            
            //Set the index number of the first PCR value
            pcrEntry.setPcrNumber(new BigInteger(new Integer(i).toString()));

            //pull off the PCR chunk
            segmentSize = PCR_SIZE;
            pcrValue = tpmOutput.substring(sizeCounter, sizeCounter+(segmentSize*2));
            
            //parse the PCR value
            try
            {
                pcrValueBytes = unHexString(pcrValue);
            }
            catch(Exception e)
            {
                s_logger.error( "Error parsing PCR Value hex string #"+i+" , invalid input. Error at count: "+ sizeCounter + ".\n"+ tpmOutput,  e);
                throw new IOException("Error parsing PCR Value hex string #"+i+" , invalid input.");
            }     
            
            //set the value and add it to the list
            pcrEntry.setValue(pcrValueBytes);
            pcrs.add(pcrEntry);
            
            sizeCounter = sizeCounter+(segmentSize*2);
            
            
            //Check for the space?
            if(tpmOutput.charAt(sizeCounter)!=' ')
            {
                s_logger.error("Parsing error at PCR #"+i+". Unexpected length. Error at count: "+ sizeCounter + ".\n"+ tpmOutput);
                throw new IOException("Parsing error at PCR #"+i+". Unexpected length.");
            }
            //One for the space
            sizeCounter++;
        }
        
        s_logger.debug("Parsing quote.");

        //--------------------INPUT NONCE------------------------//

        //just skip it and the extra space after it.  The nonce is also included in the Quote
        sizeCounter = sizeCounter+(NONCE_SIZE*2)+1;
        
        //--------------------QUOTE------------------------------//        
      
        
        //parse Version 1 quote
        if(quoteVer==1)
        {
            segmentSize = QUOTE_SIZE;
            //Get the quote version
            try
            {
                quoteVersionTag = tpmOutput.substring(sizeCounter, sizeCounter+QUOTE_VERSION_SIZE*2);
            }
            catch(Exception e)
            {

                s_logger.error( "Unable to read TPM Quote Version Hex String!  Error at count: "+ sizeCounter +".\n"+ tpmOutput,  e);
                throw new IOException("Unable to read TPM Quote Version Hex String!");
            }

            if(!quoteVersionTag.equals(EXPECTED_QUOTE_VERSION_TAG))
            {
                s_logger.warn( "Quote version "+quoteVersionTag+ " does not match expected version "+EXPECTED_QUOTE_VERSION_TAG);
            }

            sizeCounter = sizeCounter+(QUOTE_VERSION_SIZE*2);
            //skip over the fixed value
            sizeCounter = sizeCounter+(QUOTE_FIXED_SIZE*2);

            //Get the PCR Hash PCR_HASH_SIZE
            pcrHash = tpmOutput.substring(sizeCounter, sizeCounter+PCR_HASH_SIZE*2);
            sizeCounter = sizeCounter+(PCR_HASH_SIZE*2);

            //Get the nonce
            returnedNonce = tpmOutput.substring(sizeCounter, sizeCounter+NONCE_SIZE*2);
            sizeCounter = sizeCounter+(NONCE_SIZE*2);

            bitmaskLen=bitmask.length;//PCR_BITMASK_SIZE;

        }
        
        //parse Version 2 quote
        if(quoteVer==2)
        {
            //QUOTE 2 can have a variable length of between 50 and 52 bytes

            //Get the quote version/tag  The value for Quote 2 is 0x0036
            try
            {
                quoteVersionTag = tpmOutput.substring(sizeCounter, sizeCounter+QUOTE2_VERSION_SIZE*2);

                if(!Arrays.equals(unHexString(quoteVersionTag), EXPECTED_QUOTE2_VERSION_TAG))
                {
                    s_logger.warn( "Quote version "+quoteVersionTag+ " does not match expected version "+hexString(EXPECTED_QUOTE2_VERSION_TAG));
                }        
            }
            catch(Exception e)
            {

                s_logger.error( "Unable to read TPM Quote Version Hex String!  Error at count: "+ sizeCounter +".\n"+ tpmOutput,  e);
                throw new IOException("Unable to read TPM Quote Version Hex String!");
            }

            sizeCounter = sizeCounter+(QUOTE2_VERSION_SIZE*2);
            //skip over the fixed value
            sizeCounter = sizeCounter+(QUOTE_FIXED_SIZE*2);

            //Get the nonce
            returnedNonce = tpmOutput.substring(sizeCounter, sizeCounter+NONCE_SIZE*2);
            sizeCounter = sizeCounter+(NONCE_SIZE*2);

            bitmaskLenStr = tpmOutput.substring(sizeCounter, sizeCounter+QUOTE2_BITMASK_LENGTH_SIZE*2);
            sizeCounter = sizeCounter+(QUOTE2_BITMASK_LENGTH_SIZE*2);

            //pull the size of the bitmask in bytes from the length field
            if(bitmaskLenStr.endsWith("1")){bitmaskLen=1;}
            else if(bitmaskLenStr.endsWith("2")){bitmaskLen=2;}
            else if(bitmaskLenStr.endsWith("3")){bitmaskLen=3;}

            //calculate the segment size
            segmentSize = QUOTE2_SIZE_BASE+bitmaskLen;

            //pull out the bitmask
            try
            {
                bitmask = unHexString(tpmOutput.substring(sizeCounter, sizeCounter+bitmaskLen*2));
                sizeCounter = sizeCounter+(bitmaskLen*2);
            }
            catch(Exception e)
            {
                s_logger.error( "Error parsing Quote 2 bitmask hex string, invalid input.",  e);
                throw new IOException("Error parsing Quote 2 bitmask hex string, invalid input.");
            }

            //Parse the one byte filler byte
            if(!tpmOutput.substring(sizeCounter, sizeCounter+2).equals(QUOTE2_FILLER_BYTE))
            {
                s_logger.warn("TPM Quote 2 Filler byte incorrect: "+tpmOutput.substring(sizeCounter, sizeCounter+2));
            }
            sizeCounter = sizeCounter+2;

            //Get the PCR Hash PCR_HASH_SIZE
            pcrHash = tpmOutput.substring(sizeCounter, sizeCounter+PCR_HASH_SIZE*2);
            sizeCounter = sizeCounter+(PCR_HASH_SIZE*2);

        }
        
        //Check for the space
        if(tpmOutput.charAt(sizeCounter)!=' ')
        {
            s_logger.error( "Parsing error in TPM Quote - "+tpmOutput.charAt(sizeCounter)+" - Unexpected length at count " + sizeCounter + ".\n"+ tpmOutput);
            throw new IOException("Parsing error in TPM Quote - "+tpmOutput.charAt(sizeCounter)+" - Unexpected length at count " + sizeCounter);
        }  
        
        sizeCounter++;
        
        //----------------------------SIGNATURE------------------------------//
        
        s_logger.debug("Parsing signature.");

        segmentSize=SIGNATURE_SIZE;

        
        //grab the signature block using the dynamic size
        signature = tpmOutput.substring(sizeCounter, sizeCounter+segmentSize*2);
        
        //Now we populate the TPM signature information
        //First set the signature method
        SignatureMethodType sm = new SignatureMethodType();
        sm.setAlgorithm("");
        quoteSig.setSignatureMethod(sm);
        //Then the key info
        //NOTE Currently no info on the signature/cert/key are available from the TPM so default values are used
        KeyInfoType ki = new KeyInfoType();        
        JAXBElement<String> kn = new org.w3._2000._09.xmldsig_.ObjectFactory().createKeyName(computerName);
        ki.getContent().add(kn);
        quoteSig.setKeyInfo(ki);


        //Finally add the actual signature bytes
        SignatureValueType sigVal = new SignatureValueType();
        
        try
        {
            sigVal.setValue(unHexString(signature));
        }
        catch(Exception e)
        {
            s_logger.error( "Error parsing signature hex string, invalid input.",  e);
            throw new IOException("Error parsing signature hex string, invalid input.");
        }

        quoteSig.setSignatureValue(sigVal);


        //------------------------QUOTE DATA PROCESSING --------------------------------//

        s_logger.debug("Processing Quote data.");
        

        //here all of the info we just parsed from the quote is assembled into a Quote Data structure
        pcrComposite.setValueSize(new BigInteger(new Integer(PCR_SIZE).toString()));
        
        pcrSelect.setPcrSelect(bitmask);
        pcrSelect.setSizeOfSelect(bitmaskLen);
        pcrComposite.setPcrSelection(pcrSelect);
        
        //parse the nonce
        try
        {
            nonceBytes = unHexString(returnedNonce);
        }
        catch(Exception e)
        {
            s_logger.error( "Error parsing nonce hex string, invalid input.",  e);
            throw new IOException("Error parsing nonce hex string, invalid input.");
        }
        //parse the PCR Composite
        try
        {
            pcrHashBytes = unHexString(pcrHash);
        }
        catch(Exception e)
        {
            s_logger.error( "Error parsing PCR Compisite hash hex string, invalid input.",  e);
            throw new IOException("Error parsing PCR Compisite hash hex string, invalid input.");
        }

        //now create the Quote Data object in the report
        quote = createQuoteDataEntry(reportID, pcrComposite, quoteSig, nonceBytes, pcrHashBytes, (short)quoteVer);
  

        return quote;
    }



  
    /** Makes a web service call to get the parameters for the Integrity check.  
     * The parameters include a Quote type (1 or 2) nonce and PCR selection and are customized for the computer and user.
     *     
     * @param userName The username of the principal logging in
     * @param compName The name of the workstation being logged into
     * 
     */
    private void getReportParams(String userName, String compName)
    {
        //get the web service object with the correct computer name
        NonceSelect nonceSelect = hisAuthenticationWebService.getNonce(compName, userName);

        //NOTE: This can be considered input filtering for command line arguments as the Hex tool can only generate 0-9 A-F
        //This selection must account for null return from older versions of the web service
        try
        {
            if(nonceSelect.getQuote() == Quote.QUOTE_2)
            {
                quoteType = 2;
            }
            else
            {
                quoteType = 1;
            }
        }
        catch(Exception e){quoteType = 1;}
        System.out.println("Quote type = "+quoteType);
        nonce = hexString(nonceSelect.getNonce());
        rawBitmask = hexString(nonceSelect.getSelect());
        
    }
    
    /** Marshalls the integrity report object into a string format and sends it to the server via a web service
     * 
     * @param reportType The integrity report to be sent
     */
    
    private void sendIntegrityReport(ReportType reportType) throws Exception
    {
        
        JAXBContext jc=null;
        Marshaller m=null;
        
        System.out.println("Sending integrity report");
        try
        {
            jc = JAXBContext.newInstance( "org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_" );
            m = jc.createMarshaller();
        }
        catch(Exception e)
        {
            throw new Exception( "Error setting up TCG report marshaller: " + e.getMessage() );
        }                
                
        //make an output stream for the marshaller and then turn the object into bytes
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        
        try
        {
            JAXBElement<ReportType> report = new org.trustedcomputinggroup.xml.schema.integrity_report_v1_0_.ObjectFactory().createReport(reportType);
            m.marshal(report, bOut);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception( "Error marshalling TCG Integrity report: " + e.getMessage() );
        }

        //Make a web service call to send the report
        try
        {
            hisAuthenticationWebService.postIntegrityReport(new String(bOut.toByteArray()));
        }
        catch(Exception e)
        {
            throw new Exception( "Web service error: " + e.getMessage() );
        }
        

    }

    /** Performs a system call to reboot the system to enable fresh measurements to be taken.
     * This is not intended as a sophisticated administrative tool, only a quick and dirty proof of concept/
     *
     * @param osType The type of OS runnign the application
     */

    private void restartComputer(int osType)
    {
        Runtime rt = Runtime.getRuntime();
        String cmd = "";
        String cmd1 = "shutdown";

        //try to send an OS specific command based on the configured OS type or if not configured, use both
        switch(osType)
        {
            case WINDOWS_OS:
                cmd = WINDOWS_REBOOT_CMD;
                break;
            case LINUX_OS:
                cmd = LINUX_REBOOT_CMD;
                break;
            default:
                //If no OS is specified then just try both commands.
                cmd = WINDOWS_REBOOT_CMD;
                cmd1 = LINUX_REBOOT_CMD;

        }

        try
        {
            rt.exec(cmd);
            //if this is "shutdown" then nothing will happen, not like it matters anyway with a reboot
            rt.exec(cmd1);
        }
        //If the command fails there's no much we can really do except possibly send an error report
        catch (IOException ioe)
        {
            System.out.println("Error rebooting client.");
            ioe.printStackTrace();

            ReportType report = createEmptyReport("Error rebooting client: "+ ioe.getMessage(), ERROR_MESSAGE_ID);

            try
            {
                sendIntegrityReport(report);
            }
            catch(Exception e)
            {
                System.out.println("Error sending error report: "+e.getMessage()+"\nI give up.");
                e.printStackTrace();
            }
        }

        return;
    }
    
    /** A wrapper method to log and send an error report with the specified error message
     * 
     * @param errMsg The error message
     * @return true for sucess, false for a sending error
     */

    private boolean sendErrorReport(String errMsg)
    {

        s_logger.error( errMsg);

        //create a new Integrity report of the empty/error type
        ReportType report = createEmptyReport(errMsg, ERROR_MESSAGE_ID);

        //then send it using the normal method
        try
        {
            sendIntegrityReport(report);
        }
        catch(Exception e)
        {
            s_logger.error("Error sending error report after \""+errMsg+"\" error: "+e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    /** Processes generic commands from the on demand system. 
     * Takes in an index which points to a command in a properties file and applies the arguments.
     * 
     * @param commandLabel The command index in the properties file
     * @param args The arguments applied to the command
     */

    private void commandProcessor(String commandLabel, String args)
    {
        Runtime rt = Runtime.getRuntime();
        String commandRoot="";
        String command="";
        int exitVal=99999;
        int i = 0 ;

        //pull the command from the property specified by the index
        commandRoot = hisProperties.getProperty(commandLabel);

        if(commandRoot==null || commandRoot.length() == 0)
        {
            sendErrorReport( "Command not found at specified index.");
            return;
        }

        //assemble the command, the arg string is single quote escaped to prevent command injection
        command= commandRoot + " \'"+args+"\'";

        try
        {
            //Run the process
            Process proc = rt.exec(command);

            //now loop until we get a return value or we reach a timeout
            while(i<=blockingTimeout)
            {

                try
                {
                    exitVal = proc.exitValue();
                }
                catch(Exception e)
                {
                    //if the process has not completed we get an exception, just catch and loop
                    Thread.sleep(1);
                }
                if(exitVal!=99999)
                {
                    //if we get a valid exit code break out of the loop
                    break;
                }

                i++;
            }

            //if i has reached the blocking timeout value we need to throw an error
            if(i>=blockingTimeout)
            {
                System.out.println("No Return value from "+command+" command.");
                s_logger.warn("No Return value from "+command+" command.");
                return;
                //TODO report this via an error report?
            }

            //if we get a non-0 return value then we also have a problem and should report it to the server
            if(exitVal!=0)
            {
                System.out.println("Error code returned from "+command+" command: "+exitVal);
                sendErrorReport("Error code returned from "+command+" command: "+exitVal);
                return;
            }

        }
        catch(Throwable t)
        {
            t.printStackTrace();
            sendErrorReport("Error running On Demand command: "+t.getMessage());
            s_logger.error("Error running On Demand command: "+t.getMessage(), t);
            return;
        }

        
    }

    /** Generated a UUID vased on the supplied version number.  Currently UUID versions 1, 3 and 4 are supported.
     * Unsupported versions will result in a default value being returned.
     *
     * @param version the version number of the UUID to be geerated.  Ver 1, 3 and 4 supported.
     * @return a properly formatted UUID
     */

    public static String generateUUID(String version)
    {
        String uuid="";

        if(version.equals("1"))
        {
            //UUID Version 1 uses MAC address and timestamp
            com.eaio.uuid.UUID u = new com.eaio.uuid.UUID();
            uuid=u.toString();
        }
        else if(version.equals("3"))
        {
            //UUID version 3 uses the machine's full domain name
            byte[] name;
            try
            {
                name = InetAddress.getLocalHost().getCanonicalHostName().getBytes();
            }
            catch(Exception e)
            {
                //if we can't find the official hostname use localhost
                s_logger.error( "Unable to obtain POSIX domain nane for Version 3 UUID.  Using default name localhost.", e);
                name = "localhost".getBytes();
            }
            java.util.UUID u = java.util.UUID.nameUUIDFromBytes(name);
            uuid = u.toString();

        }
        else if(version.equals("4"))
        {
            //UUID version 4 uses a random number
            java.util.UUID u = java.util.UUID.randomUUID();
            uuid = u.toString();
        }
        else
        {
            s_logger.error( "Invalid UUID version: "+ version+ ". Using random UUID as default.");
            java.util.UUID u = java.util.UUID.randomUUID();
            uuid = u.toString();
        }

        return uuid;
    }

}

/** Helper class that provides general stream printing capability to Std out
 *
 *
 */

//This was copied from an online template with minimal modifications
class StreamPrinter extends Thread
{
    InputStream is;
    String type;
    StandaloneHIS his;
    private volatile Thread streamPrinterThread;

    /** Basic constructor
     *
     * @param is The output stream to print from, either stdErr or stdOut
     * @param type String label for the stream being printed
     * @param h Pointer to the HIS Client in order to use its logging functions
     */

    StreamPrinter(InputStream is, String type, StandaloneHIS h)
    {
        this.is = is;
        this.type = type;
        this.his = h;
    }

    /** Main method of class.  Pulls anything written to the stream and sends it to the HIS Client logger
     *
     */
    
    public void run()
    {
        try
        {
            //prepare a buffered reader to read from
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;

            //loop until thread terminated
            while ( (line = br.readLine()) != null)
            {
                //Log to the HIS plugin logger
                his.s_logger.error("Executable interface output: " +type + "> " + line);
                System.out.println(type + ">" + line);   
            }
        } 
        catch (IOException ioe)
              {
                ioe.printStackTrace();  
              }
    }

    /** Starts the thread
     *
     *
     */
    public void start (boolean RequestTempCertRButton)
    {
        
        streamPrinterThread = new Thread (this);
        streamPrinterThread.start ();
    }
    
    /** Stops the thread
     *
     */
    public void stopThread ()
    {
        streamPrinterThread = null;
    }
}

/** Helper class that returns all of the output from the specified stream into a string
 *
 */

//This was copied from an online template with minimal modifications
class StreamOutput extends Thread
{
    InputStream is;
    String output;
    StandaloneHIS his;
    private volatile Thread streamOutputThread;

    /** Basic constructor
     *
     * @param is The output stream to print from, either stdErr or stdOut
     * @param h Pointer to the HIS Client in order to write to its tpmOutput buffer
     */
    StreamOutput(InputStream is, StandaloneHIS h)
    {
        this.is = is;
        his=h;
    }

    /** Main method of class.  Pulls anything written to the stream and sends it to the HIS Client tpmOutput buffer
     *
     */

    public void run()
    {
        
        try
        {
            //set up the buffered reader
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String line=null;
            //keeps reading until terminated
            while ( (line = br.readLine()) != null)
            {
                //concatonate the new line to the existing output
                his.tpmOutput = sb.append(line).toString();
            }
            
            //System.out.println(hisPlug.tpmOutput);
        } 
        catch (Exception ioe)
        {
                his.tpmOutput = "";
                ioe.printStackTrace();  
        }
            
    }
    /** Starts the thread
     *
     */
    public void start (boolean RequestTempCertRButton)
    {
        
        streamOutputThread = new Thread (this);
        streamOutputThread.start ();
    }
    
    /** Stops the thread
     *
     */
    public void stopThread ()
    {
        streamOutputThread = null;
    }    
    
}



