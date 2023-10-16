// Variables and Data Types
let firstName = "John"; // Declaring a string variable
const age = 30; // Declaring a constant integer variable

// Arithmetic Operations
let sum = 10 + 5; // Addition
let difference = 10 - 5; // Subtraction
let product = 10 * 5; // Multiplication
let quotient = 10 / 5; // Division
let remainder = 10 % 3; // Modulus (remainder)

// Conditional Statements
if (age >= 18) {
    console.log(firstName + " is an adult.");
} else {
    console.log(firstName + " is a minor.");
}

// Functions
function greet(name) {
    return "Hello, " + name + "!";
}

let greeting = greet(firstName);
console.log(greeting);

// Arrays
let colors = ["red", "green", "blue"];
console.log(colors[0]); // Accessing array elements
colors.push("yellow"); // Adding an element to the end of the array
console.log(colors.length); // Getting the length of the array

// Loops
for (let i = 0; i < colors.length; i++) {
    console.log(colors[i]);
}

// Objects
let person = {
    firstName: "Alice",
    lastName: "Smith",
    age: 25
};

console.log(person.firstName); // Accessing object properties
