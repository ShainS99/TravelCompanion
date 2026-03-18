# Travel Companion App

A simple Android app built in Android Studio the helps users perform travel related conversions. 
The app allows users to convert between currencies, fuel related units and temperatures through a clean and easy to use interface.

## Features
* Tab based interface
* Currency conversion
* Fuel related conversion
* Temperature conversion

## Conversion Categories
### Currency
The app supports these currencies:
* USD - United States Dollar
* AUD - Australian Dollor
* EUR - Euro
* JPY - Japanese Yen
* GBP - Great British Pound

Fixed rates used:
* 1 USD = 1.55 AUD
* 1 USD = 0.92 EUR
* 1 USD = 148.50 JPY
* 1 USD = 0.78 GBP

### Fuel
The app supports the following fuel related conversions
* Miles per gallon (mpg) - Kilometeres per Liter (km/L)
* Gallon - Liters
* Nautical Mile - Kilometers

Fixed rates used:
* 1 mpg = 0.425 km/L
* 1 Gallon = 3.785 Litres
* 1 Mile = 1.852 Kilometeres

### Temperature
The app supports conversion between any of the following:
* Celsius
* Fahrenheit
* Kelvin

Formulas used:
* Fahrenheit = (Celsius * 1.8) + 32
* Celsius = (Fahrenheit - 32) / 1.8
* Kelvin = Celsius + 273.15

## How it works
1. The user selects a conversion category using the tabs
2. The user chooses source and destination units from the spinner menus
3. The user inputs a value
4. The user presses the convert button.
5. The app performs the conversion using the fixed rates or formulas
6. The result is displayed on screen

