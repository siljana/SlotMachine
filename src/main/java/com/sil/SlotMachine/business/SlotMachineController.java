package com.sil.SlotMachine.business;

import com.sil.SlotMachine.model.PlayOutput;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class SlotMachineController {

    /* Configurable currency unit for the win */
    @Value("${wins.currency:Euro}")
    private String winlinePriceCurrency;

    /* Configurable number of matrix rows for selecting as a subset */
    @Value("${numberOfMachineMatrixRows:3}")
    private int numberOfRows;

    @Autowired
    private Environment environment;

    @Operation(summary = "Play a slot machine game.")
    @PostMapping("/play")
    public PlayOutput playResult() {
        SlotMachineMatrix slotsSelection = new SlotMachineMatrix(numberOfRows);
        log.debug("Current slot machine subset is:\n" + slotsSelection);

        List<Winline> winlines = getWinlines(slotsSelection);
        log.debug("Winlines for this subset: " + winlines.toString());

        Float totalWin = calculateWinTotalAmount(winlines);
        log.info("Slot machine play. " + winlines.size() + " winlines were found. " +
                "Total win amount: " + totalWin + " " + winlinePriceCurrency + ".");

        return new PlayOutput(slotsSelection, winlines, totalWin, winlinePriceCurrency);
    }

    /**
     * Traverse the slot machine matrix, starting from reel1 to find the winlines.
     * Compare the current element with the adjacent elements from the next reel to check for horizontal or diagonal
     * match. For the found elements compare with the next reel to get only complete winlines.
     *
     * @param slotMatrix - Selected subset as SlotMachineMatrix to search in for winline
     * @return List of all found winline in the matrix.
     */
    public List<Winline> getWinlines(SlotMachineMatrix slotMatrix) {
        List<Winline> allWinlines = new ArrayList<>();
        for (int reel1ElementIndex = 0; reel1ElementIndex < numberOfRows; reel1ElementIndex++) {
            String currentElement = slotMatrix.getReel1SelectedElements().get(reel1ElementIndex);

            List<Integer> filteredReel2 = getNextReelMatchingElementsIndexes(reel1ElementIndex,
                    currentElement, slotMatrix.getReel2SelectedElements());

            for (Integer filteredReel2ElementIndex : filteredReel2) {
                List<Integer> filteredReel3 = getNextReelMatchingElementsIndexes(filteredReel2ElementIndex,
                        currentElement, slotMatrix.getReel3SelectedElements());

                for (Integer filteredReel3ElementIndex : filteredReel3) {
                    Winline winningSequence = new Winline(reel1ElementIndex, filteredReel2ElementIndex,
                            filteredReel3ElementIndex, currentElement);

                    allWinlines.add(winningSequence);
                }
            }
        }

        return allWinlines;
    }

    private List<Integer> getNextReelMatchingElementsIndexes(
            int reelElementIndex, String elementValue, List<String> nextReel
    ) {
        // Compare only with adjacent elements from the next reel. Get their index.
        int start = reelElementIndex == 0 ? 0 : reelElementIndex - 1;
        int end = reelElementIndex == numberOfRows - 1 ? reelElementIndex : reelElementIndex + 1;

        List<Integer> foundElements = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (nextReel.get(i).equals(elementValue)) {
                foundElements.add(i);
            }
        }

        return foundElements;
    }

    public Float calculateWinTotalAmount(List<Winline> winlines) {
        Float winTotalAmount = (float) 0;
        for (Winline winline : winlines) {
            winTotalAmount += getWinAmountFromConfig(winline);
        }

        return winTotalAmount;
    }

    private Float getWinAmountFromConfig(Winline winline) {
        String winningElement = winline.getWinlineWinningElement();
        String definedPrice = environment.getProperty("wins." + winningElement);

        return definedPrice != null ? Float.parseFloat(definedPrice) : 0;
    }
}
