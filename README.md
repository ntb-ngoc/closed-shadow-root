# Handle Closed Shadow Root by Selenium v4.0

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Usage](#usage)
- [Code Explanation](#code-explanation)
- [License](#license)

## Overview

In modern web applications, shadow DOMs are used to encapsulate elements and styles, making them inaccessible from the main DOM by default. However, in automated testing scenarios, it might be necessary to access and manipulate the contents of shadow DOMs. This project shows how to use Selenium WebDriver to override the `attachShadow` method, ensuring that all shadow DOMs are created in `open` mode, which makes them accessible for testing.

## Prerequisites

- **Java Development Kit (JDK)**: Ensure you have JDK 11 or higher installed.
- **Selenium**: This project uses selenium-java-4.23.1. Download it from [here](https://www.selenium.dev/downloads/).
- **ChromeDriver**: You need the ChromeDriver binary to interact with Chrome. Download it from [here](https://sites.google.com/chromium.org/driver/).

## Setup

1. **Clone the repository**:
    ```bash
    git clone https://github.com/ntb-ngoc/closed-shadow-root
    cd closed-shadow-root
    ```

2. **Set up ChromeDriver**:
   - Download the correct version of ChromeDriver for your Chrome browser.
   - Extract the `chromedriver` binary and place it in a known location.
   - Update the `System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");` line in the Java code with the correct path.

3. **Build the Project**:
   - To build and run from the command line:
     ```bash
     mvn clean install
     ```

## Usage

1. **Run the `shadowRoot`**:
   - After setting up, run the `shadowRoot.java` file.
   - The code will:
     - Start a Chrome browser session.
     - Inject JavaScript that overrides the `attachShadow` method.
     - Navigate to the specified URL and perform your automated testing with the overridden behavior in place.
   - The shadow DOMs created on the page will now have their `mode` set to `open`.

2. **Customize the URL**:
   - In the `driver.get("https://letcode.in/shadow");` line, replace `"https://letcode.in/shadow"` with the URL of the page you want to test.

## Code Explanation

- **Saving the Original Method**: The original `attachShadow` method is saved to `Element.prototype._attachShadow`.
- **Overriding the Method**: The `attachShadow` method is overridden to force the `mode` to `open`, regardless of the mode requested.
- **Injection via CDP**: The script is injected using Chrome DevTools Protocol (CDP) with Selenium's `executeCdpCommand` method. This ensures the script runs before any other content loads on the page.

```java
String code = "Element.prototype._attachShadow = Element.prototype.attachShadow;" +
              "Element.prototype.attachShadow = function(init) {" +
              "    if (init && init.mode !== 'open') {" +
              "        init.mode = 'open';" +
              "    }" +
              "    return this._attachShadow(init);" +
              "};";
```

- **Execution**:  This injection is applied to every new document loaded during the WebDriver session, ensuring consistent behavior across all pages.

## License
This project is licensed under the MIT License. Feel free to use, modify, and distribute as needed.