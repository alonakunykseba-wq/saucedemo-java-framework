# 📋 Test Automation Plan: SauceDemo & OrangeHRM

## 1. Objective
This document outlines test scenarios designed to validate the functional integrity, data logic, and UI stability of the SauceDemo platform, with advanced synchronization practiced on OrangeHRM.

---

## 2. SauceDemo Scenarios

### Category 1: Authentication & Access Control
**TC-01: verifySuccessfulLoginRedirectsToInventory (Happy Path)**
* **Goal:** Verify that a user with valid credentials can access the application.
* **Verification:** Confirm that entering a valid username and password redirects the user to the Products page (/inventory.html) and the "Products" title is visible.

**TC-02: verifyLockedOutUserDisplaysErrorMessage (Negative)**
* **Goal:** Verify system behavior for disabled accounts.
* **Verification:** Assert that entering credentials for a locked_out_user displays the error message: "Epic sadface: Sorry, this user has been locked out."


**TC-03: verifyInvalidCredentialsDisplayErrorMessage (Negative)**
* **Goal:** Verify system behavior for incorrect credentials.
* **Verification:** Assert that entering invalid password displays the error message: "Epic sadface: Username and password do not match any user in this service."
  
**TC-04: verifyMissingPasswordDisplaysRequiredErrorMessage (Negative)**
* **Goal:** Verify mandatory field enforcement.
* **Verification:** Assert that clicking Login with an empty password field displays: "Epic sadface: Password is required."

###  Category 2: Data Integrity & Catalog

**TC-05: verifyProductCatalogCountAndBrandingConsistency**
* **Goal:** Ensure all products are present and correctly named.
* **Verification:** Verify that 6 products exist, names follow the "Sauce Labs" branding, and prices are formatted with the $ symbol.

**TC-06: verifyHighToLowPriceSortingLogic**
* **Goal:** Validate the "Price (High to Low)" filter functionality.
* **Verification:** Confirm that items are rearranged in descending order of price upon selecting the filter.

---

### Category 3: Purchase Logic & Calculations

**TC-07: verifyHighestPriceItemCanBeAddedToCart**
* **Goal:** Verify the ability to identify and select the most expensive product.
* **Verification:** Ensure the system correctly identifies the item with the maximum price and allows it to be added to the cart.

**TC-08: verifyCartBadgeUpdatesWhenProductIsAdded**
* **Goal:** Verify real-time UI state changes for specific product actions.
* **Verification:** Confirm the "Add to cart" button toggles to "Remove" and the shopping cart badge updates correctly.

**TC-09: verifyCheckoutTotalsAndTaxCalculationsAreAccurate**
* **Goal:** Verify the system calculates item totals and taxes accurately.
* **Verification:** Assert that the sum of individual items matches the subtotal and the final total includes the correct tax amount.

---

### Category 4: Negative & Edge Case Testing

**TC-10: verifyCheckoutFormValidationMessagesForMissingFields**
* **Goal:** Verify field validation on the checkout information page.
* **Verification:** Ensure appropriate error messages appear when First Name, Last Name, or Postal Code are omitted.

**TC-11: verifyCartItemsPersistAfterRelogin**
* **Goal:** Validate cart state persistence.
* **Verification:** Verify that cart contents survive a logout/login cycle.

**TC-12: verifyProductDetailsMatchCatalogInformation**
* **Goal:** Verify data synchronization between the catalog and product detail pages.
* **Verification:** Assert that price and name remain consistent when navigating into a specific product's details.

**TC-13: verifyCheckoutIsPreventedForEmptyCart**
* **Goal:** Prevent invalid checkout attempts.
* **Verification:** Confirm that the checkout process cannot be initiated if the cart contains no items.
