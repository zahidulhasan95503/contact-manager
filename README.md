# Smart Contact Manager

Smart Contact Manager is a web-based application that enables users to efficiently manage their contacts. The application allows users to create, delete, and update contacts, providing a streamlined way to organize contact information.

![homepage](https://github.com/zahidulhasan95503/smart-contact-manager/assets/102205229/29a09cbe-5d14-43df-91d2-d5926def2043)


## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- - [Create Contacts](#create-contacts)
  - [Update Contacts](#update-contacts)
  - [Delete Contacts](#delete-contacts)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

Smart Contact Manager is a user-friendly web application designed to simplify contact management tasks. Whether you need to add new contacts, update existing information, or remove outdated entries, this application provides an intuitive interface to keep your contacts organized.

## Features

### Create Contacts

Easily add new contacts to the system by providing essential details such as name, phone number, email, and more. The user-friendly interface guides you through the process, ensuring accurate and complete contact information.

### Update Contacts

Keep your contacts up-to-date by making changes whenever necessary. Whether it's a change of address, a new email, or updated job information, Smart Contact Manager makes it simple to modify contact details.

### Delete Contacts

Remove contacts that are no longer relevant or necessary. When contacts become obsolete, easily delete them from the database, decluttering your list and keeping it accurate.

## Technologies Used

The Smart Contact Manager application leverages a combination of frontend and backend technologies to provide a seamless user experience.

- Frontend:
  - HTML
  - CSS
  - JavaScript

- Backend:
  - Spring Boot

- Database:
  - MySQL

## Getting Started

Follow these steps to set up and run the Smart Contact Manager project on your local machine.

### Prerequisites

Before you begin, ensure you have the following prerequisites installed:

- Java Development Kit (JDK)
- MySQL Server
- Any modern web browser (Chrome, Firefox, Edge, etc.)


## Installation

1. Clone the repository:

```bash
  git clone https://github.com/zahidulhasan95503/smart-contact-manager.git
```
2. Navigate to the project directory:    

```bash
 cd smart-contact-manager
```
## Configuration

1. Configure the database settings in 'src/main/resources/application.properties':

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/contactdb
spring.datasource.username=your-username
spring.datasource.password=your-password
```

2. Build the project using Maven:

```bash
./mvnw clean package
```

3. Run the Application:

```bash
java -jar target/smart-contact-manager.jar
```
The application will be accessible at http://localhost:8080.


## Usage

1.Open your web browser and navigate to http://localhost:8080.

2.Use the provided interface to create, update, and delete contacts as needed.

3.Enjoy efficient contact management!


## Contributing

Contributions are welcome! If you find any issues or want to enhance the functionality of the Smart Contact Manager, feel free to create pull requests or open issues in the GitHub repository.

When contributing, please follow the existing coding style, provide clear commit messages, and ensure proper testing.

