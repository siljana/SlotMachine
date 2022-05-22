package com.sil.SlotMachine;

import com.sil.SlotMachine.business.SlotMachineController;
import com.sil.SlotMachine.business.SlotMachineMatrix;
import com.sil.SlotMachine.business.Winline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class SlotMachineControllerJUnitTests {

    private SlotMachineMatrix slotMatrix;
    @Autowired
    private SlotMachineController slotMachineController;
    @Autowired
    private Environment environment;

    @BeforeEach
    public void setUp() {
        slotMatrix = new SlotMachineMatrix(3);
        slotMatrix.setReel1SelectedElements(List.of("7", "O", "X"));
        slotMatrix.setReel2SelectedElements(List.of("7", "7", "O"));
        slotMatrix.setReel3SelectedElements(List.of("7", "X", "7"));
        /* resulting in the Matrix:
            7 7 7
            O 7 X
            X O 7
         */
    }

    @Test
    @DisplayName("Test finding the winlines in the slot machine matrix.")
    public void testGetWinline() {
        final List<Winline> winlines = new ArrayList<>(Arrays.asList(
                new Winline(0, 0, 0, "7"),
                new Winline(0, 1, 0, "7"),
                new Winline(0, 1, 2, "7")
        ));

        assertEquals(winlines, slotMachineController.getWinlines(slotMatrix));
    }

    @Test
    @DisplayName("Test calculation of the total win amount")
    public void testCalculateWinTotalAmount() {
        final List<Winline> winlines = new ArrayList<>(Arrays.asList(
                new Winline(0, 0, 0, "7"),
                new Winline(0, 1, 0, "7"),
                new Winline(0, 1, 2, "7")
        ));
        String definedPrice = environment.getProperty("wins.7");
        float price7;
        if (definedPrice != null) {
            price7 = Float.parseFloat(definedPrice);
        } else {
            price7 = (float) 0;
        }

        assertEquals(winlines.size()*price7, slotMachineController.calculateWinTotalAmount(winlines));
    }
}
