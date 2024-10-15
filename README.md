# Order API

## Overview
The Order API is designed to handle the order creation process and integrates with several APIs to manage coupons, store stock, banking transactions, and notifications. Below is a detailed breakdown of how the order process works.

## Order Creation Process

### 1. Coupon Validation and Consumption
- When an order is created, if the customer applies a coupon, the system validates the coupon using the **Coupon API**.
- Once validated, the coupon is marked as consumed to prevent future use.

### 2. Stock Consumption
- After validating the coupon, the system checks product availability by calling the **Store API**.
- It ensures that the required stock is available and reduces the stock based on the quantity ordered.

### 3. Payment Processing
- The system integrates with the **Bank API** to handle payments:
  - Withdraws the total order amount from the customer's account and saves the transaction ID for record-keeping.
  - Deposits the order amount into the merchant’s account, completing the payment process.

### 4. Order Notifications
- Once the order is successfully created and processed, notifications are sent out via the **Notification API** to keep both the customer and merchant informed about the order status via email or SMS.

## Order Search Functionality
The application provides the ability to search for orders based on:
- **Customer Information**: Search for all orders associated with a specific customer.
- **Date Range**: Filter orders by a specific date range for efficient reporting and analysis.

## Customer Portal
The application includes a **Customer Portal** where users can:
- **View Products**: Display a list of available products for purchase.
- **Checkout**: Once products are selected, the customer can proceed to checkout, triggering the order creation process as detailed above.

## ERD

```mermaid
erDiagram
  CUSTOMER {
        NUMBER(4) ID PK
        VARCHAR2(50) Name
        VARCHAR2(30) Email
        VARCHAR2(20) Phone_Number
        varchar(16)  ACCONT_NUMBER
    }
       
   ORDERS{
       NUMBER(4) id PK
       VARCHAR2(20) reference_number
       NUMBER(15) amount
       NUMBER(15) total_amount
       NUMBER(10) discount_value
       NUMBER(5)  discount_percentage
       NUMBER(4)  customer_id FK
       NUMBER(4)   merchant_id FK
       Data   CREATED_DATE     
   }

    ORDER_ITEMS{
        NUMBER(4) Id PK
        NUMBER(4) order_id FK  
        NUMBER(4) product_id FK 
        VARCHAR2(10) quantity
        NUMBER(4) total_price
    }

   PRODUCT {
        NUMBER(5) Id PK
        VARCHAR2(100) Name
        VARCHAR2(8) sku
        VARCHAR2(3) Code
        VARCHAR2(30) price
        NUMBER(8) merchant_id FK
        NUMBER(8) category_id FK
        VARCHAR2(1000) image_url
        VARCHAR2(300)  description
    }
   
    CATEGORY {
        NUMBER(5) Id PK
        VARCHAR2(50) Name
        VARCHAR2(3) Code
        CHAR(1) Is_Main
    }
     
  
    MERCHANT{
        NUMBER(4) id PK
        VARCHAR(50) name
        VARCHAR(30) Email
        VARCHAR(20) phone_number
        varchar(16) ACCONT_NUMBER
   }
  
   ORDERS ||--|{ ORDER_ITEMS : contains
   CATEGORY ||--o{ PRODUCT : categorized_as
   PRODUCT ||--o{ ORDER_ITEMS : includes
   MERCHANT ||--o{ PRODUCT : owns
   MERCHANT ||--o{ ORDERS : handles
   CUSTOMER ||--o{ ORDERS : places

```
