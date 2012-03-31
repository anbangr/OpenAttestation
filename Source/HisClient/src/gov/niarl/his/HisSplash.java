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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/** Helper class that enables and shows the HIS splash image
 *
 * @author mcbrotz
 */


//This was largely copied from a template
public class HisSplash extends JWindow {

    private int duration;
    String imgFilePath;

    /** Creates a new HisSplash object.
     *
     * @param d The duration the splash screen is displayed for
     * @param path The path to the image file to be displayed.
     */
    public HisSplash(int d, String path) {
        duration = d;
        imgFilePath= path;
    }


    /** Shows the HIS Splash image
     *
     */
    public void showSplash() {

        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.white);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        // Build the splash screen
        JLabel label = new JLabel(new ImageIcon(imgFilePath));


        //set the image to be displayed in the middle of the screen
        Dimension labelSize = label.getPreferredSize();
        int x = (screen.width-labelSize.width)/2;
        int y = (screen.height-labelSize.height)/2;
        setBounds(x,y,labelSize.width,labelSize.height);


        content.add(label, BorderLayout.CENTER);

        //allow the users to click the image and make it disappear.
        addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    setVisible(false);
                    dispose();
                }
            });

        ///inner class that displays and runs the splash screen in its own thread.
        final int pause = duration;
        final Runnable closerRunner = new Runnable()
            {
                public void run()
                {
                    setVisible(false);
                    dispose();
                }
            };
        Runnable waitRunner = new Runnable()
            {
                public void run()
                {
                    try
                        {
                            Thread.sleep(pause);
                            SwingUtilities.invokeAndWait(closerRunner);
                        }
                    catch(Exception e)
                        {
                            e.printStackTrace();
                            // can catch InvocationTargetException
                            // can catch InterruptedException
                        }
                }
            };
        setVisible(true);
        Thread splashThread = new Thread(waitRunner, "SplashThread");
        splashThread.start();
        

        // Display it
        setVisible(true);

        // Wait a little while, maybe while loading resources
        try 
        { 
        	Thread.sleep(0); 
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }



    }

    /** Shows the HIS Splash image and exits.  Used for the HIS branding mode.
     *
     */
    public void showSplashAndExit() {

        showSplash();
        System.exit(0);

    }

}