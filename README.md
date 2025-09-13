# DSCommerce
[DevSuperior](https://devsuperior.com.br/)\
<br>
My personal project of developing the back-end of DSCommerce, built during the Java Spring Professional course at DevSuperior School.
<br>
<br>
## Class Diagram
![img](https://github.com/luiz-barros-92/assets/blob/main/dscommerce/class-diagram.png)
<br>
<br>
## Use Cases
| *Use Case* | *Overview* | *Access* |
| ------------ | ------------ | ---------- | 
| Product Management | Product CRUD, with filtering by name | Admin Only |
| Category Management | Category CRUD, with filtering by name | Admin Only |
| User Management | User CRUD, with filtering by name | Admin Only |
| Cart Management | Add and remove items from cart, with quantity adjustment | Public |
| Catalog Browsing | List available products, with filtering by name | Public |
| Sign Up | Register in the system | Public |
| Login | Log in to the system | Public |
| Order Placement | Save order from cart data | Logged-in User |
| Profile Update | Update own profile | Logged-in User |
| Order History | View own order history | Logged-in User |
| Payment Recording | Record payment data for an order | Admin Only |
| Order Reporting | Order report, with filtering by date | Admin Only |
<br>
<br>

## Actors

| *Actor* | *Responsibility* |
| --------- | ------------------ | 
| Anonymous User | Can perform use cases in the public areas of the system, such as catalog, shopping cart, login, and sign up. |
| Customer | Responsible for maintaining their own personal data in the system and can view their order history. Every user registered is a Customer by default. |
| Admin | Responsible for accessing the administrative area of the system with registrations and reports. Admin can also do everything a Customer can do. |
<br>
<br>

## User-Role Model
![img](https://github.com/luiz-barros-92/assets/blob/main/dscommerce/user-role-model.png)
<br>
<br>

## Spring Security User Details Model
![img](https://github.com/luiz-barros-92/assets/blob/main/dscommerce/user-details-model.png)
