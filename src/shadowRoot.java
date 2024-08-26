import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class shadowRoot {

    static ChromeDriver driver = new ChromeDriver();

    public static void main(String[] args) throws Exception {

        // Initalize a java hash map to pass the argument to CDP command
        HashMap<String, Object> hm = new HashMap<String, Object>();
    

        /* 
         * Saving the origin 'attachShadow' method into new name '_attachShadow'
         * Define new 'attachShadow' function with parameter 'init' 
         * Check if 'init' is not null AND 'init.mode' is not 'open' 
         * Then set 'init.mode' to 'open'
         * Return new '_attachShadow' method with 'init' arguments
         */
        Object code = "Element.prototype._attachShadow = Element.prototype.attachShadow;" +
                      "Element.prototype.attachShadow = function(init) {" +
                      "    if (init && init.mode !== 'open') {" +
                      "        init.mode = 'open';" +
                      "    }" +
                      "    return this._attachShadow(init);" +
                      "};";

        // Add code to hash map with key 'source'
        hm.put("source", code);
    
        // Execute CDP Command with the hash map
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", hm);

        // Open browser and navigate to 'LetCode'
        driver.get("https://letcode.in/shadow");

        // Initialize firstName ShadowHost CSS Selector and firstName value for input field
        String fnameHost = "#open-shadow";
        String fname = "John";
        // Call function getShadowRootInput with fnameHost and fname
        getShadowRootInput(fnameHost, fname);

        // Initialize lastName ShadowHost CSS Selector and lastName value for input field
        String lnameHost = "my-web-component";
        String lname = "Doe";
        // Call function getShadowRootInput with lnameHost and lname
        getShadowRootInput(lnameHost, lname);

        // Initialize email ShadowHost CSS Selector and email value for input field
        String emailHost = "#close-shadow";
        String email = "johndoe@exe.com";
        // Call function getShadowRootInput with emailHost and email
        getShadowRootInput(emailHost, email);

        // Delay for 5 seconds
        Thread.sleep(5000);

        // Close browser
        driver.quit();
    }

    public static void getShadowRootInput(String hostPath, String value) {
        // Find web element 'shadowHost' by CSS Selector argument
        WebElement host = driver.findElement(By.cssSelector(hostPath));

        // Find web element 'input' in shadowDOM
        WebElement input = host.getShadowRoot().findElement(By.cssSelector("input"));

        // Set text for the 'input' field
        input.sendKeys(value);
    }
}
