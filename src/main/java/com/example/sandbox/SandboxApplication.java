package com.example.sandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SandboxApplication.class, args);
    }

}

//    Możesz używać dowolnych typów asercji: Hamcrestowych lub też innych.
//
//
//
//        Rzeczy warte przetestowania to m.in. konstruktor Coordinates oraz metoda copy.
//        W klasie Cargo nie ma żadnej
//        zaawansowanej logiki do sprawdzenia. W tym przypadku nie ma też potrzeby, aby testować settery, gettery
//        oraz metody equals i hashCode.
//        Natomiast w klasie Unit warto przetestować wszystkie publiczne metody
//        poza konstruktorem.

//    Coordinates(int x, int y) {
//
//        if (x < 0 || y < 0) {
//            throw new IllegalArgumentException("Position can not be less than 0");
//        }
//
//        if (x > 100 || y > 100) {
//            throw new IllegalArgumentException("Position can not be more than 100");
//        }
//
//        this.x = x;
//        this.y = y;
//    }