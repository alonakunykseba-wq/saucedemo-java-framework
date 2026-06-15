# saucedemo-java-framework

## 🚀 Project Status: Complete

## 🎯 Project Overview
This repository contains a professional‑grade UI automation framework built with Java, Selenium WebDriver, and TestNG. It is designed to demonstrate clean automation architecture, scalability, and strict adherence to Clean Code principles. 
This project tests the End-to-End (E2E) E-commerce flows of the SauceDemo web application, focusing on robust form validation, dynamic DOM handling, and mathematically verifying shopping cart algorithms.

## 🛠 Tech Stack
* **Language:** Java 21 (Records, Streams, Lambdas)
* **Automation:** Selenium WebDriver
* **Test Runner:** TestNG
* **Build Tool:** Maven
* **Reporting:** Allure
* **Assertions:** AssertJ (Soft Assertions)
* **Version Control:** Git & GitHub

## 🏗 Framework Architecture
This project implements a highly scalable architecture designed for enterprise‑level testing:

* **Strict Page Object Model (POM):** 
  * Centralized "BasePage.java" handles all core WebDriver waits and actions.
  * Complete encapsulation of UI elements "WebElements" and "By" locators are never exposed to test classes).
* **Data-Driven Testing (DDT):**
  * Utilizes TestNG "@DataProvider" matrices paired with modern **Java Records** to cleanly pass complex data objects, avoiding the "Long Parameter List" code smell.
* **Advanced Financial Validation:**
  * Uses Regex string scrubbers ("[^0-9.]") and Java Streams to extract raw financial data from the UI and mathematically verify dynamic tax algorithms (8% rate).
* **Dynamic DOM Handling:**
  * Implements resilient loop structures to overcome React StaleElementReferenceExceptions`.
* **CI/CD Ready:**
  * Configured via "testng-all-crossbrowser.xml" for cross-browser execution and "testng-smoke.xml" for categorized TestNG "@Groups" (Smoke).
    
## 📖 Test Strategy
The suite is chronologically organized by User Flow (Login -> Catalog -> Cart -> Checkout -> Edge Cases). 
  For a detailed breakdown of all test scenarios and logic, please see the [Test Plan](TEST_PLAN.md).

## 💻 How to Run
1. Ensure you have Java and Maven installed.
2. Clone the repository: `git clone [Your Repo Link]`
3. Execute tests via terminal:
   ```bash
   mvn clean test
