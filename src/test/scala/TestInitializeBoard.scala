package de.htwg

import munit.FunSuite

class TestInitializeBoard extends FunSuite {
    test("initialized Board meets all requirements") {
        val b = Board(3, 4, 12, 12, 10)

        assertEquals((b.getSize), (12,12))
        
    }

}