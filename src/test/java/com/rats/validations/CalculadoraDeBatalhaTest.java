package com.rats.validations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rats.interfaces.ShipOrientation;

@SpringBootTest
class CalculadoraDeBatalhaTest {

    @Test
    void testCalcularPosicoesPossiveis() {
        List<long[]> result = CalculadoraDeBatalha.calcularPosicoesPossiveis(70, 6, 5);
        assertEquals(12, result.size());
        assertEquals(70, result.get(0)[0]);
        assertEquals(1, result.get(0)[1]);

        assertEquals(67, result.get(1)[0]);
        assertEquals(2, result.get(1)[1]);

        assertEquals(73, result.get(2)[0]);
        assertEquals(2, result.get(2)[1]);

        assertEquals(66, result.get(3)[0]);
        assertEquals(3, result.get(3)[1]);

        assertEquals(74, result.get(4)[0]);
        assertEquals(3, result.get(4)[1]);

        assertEquals(65, result.get(5)[0]);
        assertEquals(6, result.get(5)[1]);

        assertEquals(75, result.get(6)[0]);
        assertEquals(6, result.get(6)[1]);

        assertEquals(66, result.get(7)[0]);
        assertEquals(9, result.get(7)[1]);

        assertEquals(74, result.get(8)[0]);
        assertEquals(9, result.get(8)[1]);

        assertEquals(67, result.get(9)[0]);
        assertEquals(10, result.get(9)[1]);

        assertEquals(73, result.get(10)[0]);
        assertEquals(10, result.get(10)[1]);

        assertEquals(70, result.get(11)[0]);
        assertEquals(11, result.get(11)[1]);

    }

    @Test
    void testCalcularPosicoesPossiveisComPosicaoForaDoLimite() {
        List<long[]> result = CalculadoraDeBatalha.calcularPosicoesPossiveis(-1, -1, 3);
        assertEquals(0, result.size());
    }

    @Test
    void testIsPossitionMyShipHorizontal() {
        boolean result = CalculadoraDeBatalha.isPossitionMyShip((byte) 20,  (byte) 22, (byte) 22, (byte) 22, ShipOrientation.HORIZONTAL.getOrientation());
        assertTrue(result);
    }

    @Test
    void testIsPossitionMyShipVertical() {
        boolean result = CalculadoraDeBatalha.isPossitionMyShip((byte) 60,  (byte) 15, (byte) 60, (byte) 13, ShipOrientation.VERTICAL.getOrientation());
        assertTrue(result);
    }

    // @Test

    
}
