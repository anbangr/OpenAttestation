/**************************************************************************
* 2012, U.S. Government, National Security Agency, National Information Assurance Research Laboratory
*
* This is a work of the UNITED STATES GOVERNMENT and is not subject to copyright
* protection in the United States. Foreign copyrights may apply.
*
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
*
* 鈥�Redistributions of source code must retain the above copyright notice,
*   this list of conditions and the following disclaimer.
*
* 鈥�Redistributions in binary form must reproduce the above copyright notice,
*   this list of conditions and the following disclaimer in the documentation
*   and/or other materials provided with the distribution.
*
* 鈥�Neither the name of the NATIONAL SECURITY AGENCY/NATIONAL INFORMATION ASSURANCE RESEARCH LABORATORY
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import org.apache.log4j.Logger;

/** This serves as a wrapper for the normal HIS Client that recieves a socket connection from the HIS server
 * and triggers HIS to obtain and send a quote via the normal methods.
 *
 * 
 * @deprecated Functionality replaced by the On Demand web service polling method, but left as an example alternate On Demand method.
 * @author mcbrotz
 */

public class HisListener
{

    //global String for the applications working directory
    String hisPath="./";
    //The server socket on which the messages are recieved
    ServerSocket serverSocket = null;
    //The port number
    int listenPort = 8888;

    Properties hisProperties = new Properties();
    //The timeout in approximate ms for any blocking function to wait for a process to return
    int blockingTimeout=10000;

    public static Logger s_logger = Logger.getLogger( "HIS" );

    //Property file labels and defaults
    public static final String TRUST_STORE_LABEL = "TrustStore";
    public static final String SOCKET_PORT_LABEL = "SocketListnerPort";
    public static final String DEFAULT_SOCKET_PORT = "8888";

    //Various Constants
    public static final String DEFAULT_HIS_PATH = "/OAT/";
    public static final String PROPERTIES_NAME = "OAT.properties";
    public static final String PROPERTIES_EXTENSION = ".properties";
    public static final String DEFAULT_STATE_MESSAGE = "100";
    public static final String SUCCESS_MESSAGE = "success";


     /**
     * @param args the command line arguments
     *
     * The first parameter is the working directory.
      */

    public static void main(String[] args)
    {
        StandaloneHIS his = null;
        HisListener client=null;
        String path= DEFAULT_HIS_PATH;;


        //bounds check for presence of the path argument
        if(args.length>=0)
        {
            path = args[0];
        }

        //initialize the two main modules
        try
        {
            client = new HisListener(path);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        //start the listening loop
        client.listen();
    }

    /** Begins a new HIS Listner process.
     *
     * @param path Path that the HIS Client is running in (same path parameter used in standard HIS Client)
     * @throws java.lang.Exception
     */
    public HisListener(String path) throws Exception
    {

        hisPath = path;

        FileInputStream HISPropertyFile = null;

        try
        {
            HISPropertyFile = new FileInputStream(hisPath+PROPERTIES_NAME);
        }
        catch (java.io.FileNotFoundException fnfe)
        {
            //Try to create the Property File if it is not present
            try
            {
                File fileName= new File(hisPath+PROPERTIES_NAME);  // create the directory
                fileName.createNewFile();
                HISPropertyFile = new FileInputStream(fileName);

            }
            catch (Exception ioe)
            {
                s_logger.error("HIS Property File empty.\n Unable to create new HIS property file!\n");
                throw new Exception("HIS Property File empty.\n Unable to create new HIS property file!\n");
            }
        }

        s_logger.debug( "Using HIS Property File: "+ HISPropertyFile.toString());

        //If we load in a file put it in to a proprties object
//        if(HISPropertyFile!=null)
//        {
            try
            {
                hisProperties.load(HISPropertyFile);

            }
            catch (java.io.FileNotFoundException fnfe)
            {
                s_logger.error( "HIS Property file not found on Property load!" );
                throw new Exception("HIS Property file not found on Property load!");
            }
            catch (java.io.IOException ioe)
            {
                s_logger.error( "Error loading HIS Property file!" );
                throw new Exception("Error loading HIS Property file!");
            }
            finally{
            	HISPropertyFile.close();
            }
//        }
//        else
//        {
//            s_logger.error( "Error loading HIS Property file!" );
//            throw new Exception("Error loading HIS Property file!");
//        }

        //Initialize the SSL Trust store
        String trustStoreUrl = hisProperties.getProperty(TRUST_STORE_LABEL,hisPath+"TrustStore.jks");
        System.setProperty("javax.net.ssl.trustStore", trustStoreUrl);

        //get the socket listening port
        listenPort = Integer.parseInt(hisProperties.getProperty(SOCKET_PORT_LABEL, DEFAULT_SOCKET_PORT));

        //Set up the listening socket
        try
        {
            serverSocket = new ServerSocket(listenPort);
        }
        catch (IOException e)
        {
            s_logger.error("Could not listen on port: "+listenPort, e);
            System.exit(1);
        }
    }

   /** Perpetually loops, listening for and acting upon connections
    * 
    * 
    */

   private void listen()
   {
        Socket clientSocket=null;
        PrintWriter out=null;
        BufferedReader in=null;
        String inputLine="000";
        StandaloneHIS his;


        while(true)
        {

            //Open the connection with the client
            try
            {
                //this blocks until a connection is made
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            }
            catch(IOException ioe)
            {
            	if (clientSocket != null)
            	{
            		try {
						clientSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
                s_logger.error("Could not accept connection!", ioe);
                
            }

            try
            {

                //read the input from the steam.  Initial message should be short so no need for loop here
                //NOTE One might want to add input while loop for extended messages or protocols
                if (in != null)
                {
                	 inputLine = in.readLine();
                }

                //if the input message matches we should activate the HIS Standalone app.
                if (inputLine != null)
                {
                	if(inputLine.equals(DEFAULT_STATE_MESSAGE))
                    {


                        //run the integrity check
                        try
                        {
                            //Set up the his object
                            his = new StandaloneHIS(hisPath, false, false);
                            his.checkIntegrity();
                        }
                        catch(Exception e)
                        {
                            s_logger.error("Error generating quote!", e);
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }

                        //when the integrity check completes return a courtesy success message
                        out.println(SUCCESS_MESSAGE);

                    }
                    else
                    {
                        System.out.println("Unknown recieved message: "+inputLine);
                        s_logger.warn("Unknown recieved message: "+inputLine);
                    }
                }
                

            }
            catch(IOException ioe)
            {
                 s_logger.error("Could not read from I/O stream!", ioe);
            }
            finally
            {
            	 //close the sockets and wait for the next connection
                if (in != null)
                {
                	try 
                    {
    					in.close();
    				} 
                    catch (IOException e) 
    				{
    					e.printStackTrace();
    				}
                }
                if (out != null)
                	out.close();
                if (clientSocket != null)
                {
    				try
    				{
    					clientSocket.close();
    				} 
    				catch (IOException e) 
    				{
    					e.printStackTrace();
    				}
                }
            }
        }
    }
   
}
