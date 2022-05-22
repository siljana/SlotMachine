# Slot machine example backend implementation
developed by Silyana Gerova©

This project implements the backend for a typical slot machine.

## General task
For a predefined slot machine reel matrix a 3x3 subset of elements is randomly selected. In this subset all winlines 
are calculated. A "winline" is an occurrence of 3 same symbols starting from the first column (reel1) in a
horizontal or diagonal alignment. A combination from horizontal and diagonal alignment of the elements in the winline 
is also considered to be a winline.  
The given wins for a winline are as follows:
- 3 times X wins 10 cents
- 3 times O wins 25 cents
- 3 times 7 wins 50 cents  
  
but they are configurable. The total win from all horizontal and diagonal winlines on this subset is calculated and 
returned along with information for each winline via a small API to an imaginary frontend.

### Example

#### Slot machine matrix subset
| `Coordinates` | Reel1  | Reel2 | Reel3 |  
|---------------|--------|-------|-------|
| `0`           | X      | O     | 7     |  
| `1`           | 7      | 7     | 7     |   
| `2`           | O      | X     | X     |  


#### Winlines and total win

Winline 1 at coordinates (1, 1, 0) for element `7` bringing a win from 0,5 €

| Reel1 | Reel2 | Reel3 |  
|-------|-------|-------|
| X     | O     | `7`   |  
| `7`   | `7`   | 7     |   
| O     | X     | X     |    

Winline 2 at coordinates (1, 1, 1) for element `7` bringing a win from 0,5 €

| Reel1 | Reel2 | Reel3 |  
|-------|-------|-------|
| X     | O     | 7     |  
| `7`   | `7`   | `7`   |   
| O     | X     | X     |    

**Total win amount for this turn is _1,0_ €.**

## Technology stack

- OpenJDK 17
- Maven
- Spring boot 2.6

### API-Call-Tests
- with Postman

## Configuration
**Configuration** in file: application.properties

## Usage

Starting from a command line:
```bash
java -jar SlotMachine-0.0.1.jar
```
**REST-API-Call:** POST http://localhost:8080/play  
Or use Postman-collection: SlotMachineRestAPI.postman_collection

**REST-API-Documentation** is automatically generated and available under:  
http://localhost:8080/v3/api-docs/

## Copyright
Author Silyana Gerova©. All rights reserved.