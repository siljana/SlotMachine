package com.sil.SlotMachine.business;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@JsonPropertyOrder({"reel1SelectedElements", "reel2SelectedElements", "reel3SelectedElements"})
@Schema(description = "Slot machine matrix with 3 reels, containing the randomly selected subsets of elements.")
public class SlotMachineMatrix {
    /* Initialize Reel1 with the given sequence of values */
    private final List<String> reel1 = List.of(
            "X", "7", "X", "7", "X", "7", "X", "O", "7", "7", "7", "X", "O", "7", "O", "7", "X", "7", "O", "X", "O", "X"
    );

    /* Initialize Reel2 with the given sequence of values */
    private final List<String> reel2 = List.of(
            "O", "7", "X", "O", "X", "7", "X", "7", "O", "7", "X", "X", "X", "O", "X", "O", "X", "O", "7", "O"
    );

    /* Initialize Reel3 with the given sequence of values */
    private final List<String> reel3 = List.of(
            "7", "O", "X", "X", "X", "X", "X", "7", "7", "O", "O", "7", "7", "X", "7", "X", "7", "X", "7", "X", "7"
    );

    /* List for the random selected subset from Reel1 */
    @Getter
    @Setter
    private List<String> reel1SelectedElements;

    /* List for the random selected subset from Reel2 */
    @Getter
    @Setter
    private List<String> reel2SelectedElements;

    /* List for the random selected subset from Reel3 */
    @Getter
    @Setter
    private List<String> reel3SelectedElements;

    private final int numberOfRows;

    /**
     * Constructs a slot machine matrix with 3 reels as 3 Lists of Strings, containing the randomly selected subsets
     * of elements from the initialized reels. If the subset exceeds the reel length, the subset proceeds with the
     * first element of the reel.
     * numberOfRows defines the count of the elements in the subsets (the count of the matrix rows).
     * @param numberOfRows - Number of matrix rows for selecting as a subset.
     */
    public SlotMachineMatrix(int numberOfRows) {
        this.numberOfRows = numberOfRows;
        this.reel1SelectedElements = getRandomElementsSubset(reel1);
        this.reel2SelectedElements = getRandomElementsSubset(reel2);
        this.reel3SelectedElements = getRandomElementsSubset(reel3);
    }

    /**
     * Gives a predefined number (@see numberOfRows) of elements from a randomly selected sequence from a given
     * List (reel).
     * @param reel - The given List of String elements for the randomized selection of a subset.
     * @return List with randomly selected sequence of String elements.
     */
    private List<String> getRandomElementsSubset(List<String> reel) {
        List<String> reelSubset = new ArrayList<>();

        // Get random element within the List as a starting point for the subset
        Random random = new Random();
        int randomElementIndex = random.nextInt(reel.size() - 1);
        log.debug("Reel size: " + reel.size() + " generated random starting slot: " + randomElementIndex);
        reelSubset.add(reel.get(randomElementIndex)); // Add the random element to the selected subset.

        // Get the next elements in the sequence. If the end of the reel is reached, proceed from the reel beginning.
        for (int i = 1; i < numberOfRows; i++) {
            int currentElementIndex = randomElementIndex + i;  // Get the next element
            int resetIndexing = currentElementIndex - reel.size();
            if (resetIndexing >= 0) {  // Check if the end of the array is reached and start over at index 0
                currentElementIndex = resetIndexing;
            }
            log.debug("Getting the next element with index: " + currentElementIndex);
            reelSubset.add(reel.get(currentElementIndex)); // Add the next element to the selected subset.
        }

        return reelSubset;
    }

    /**
     * A formatted String representation of the slot machine matrix. The reels are presented vertical (the elements
     * are space-separated, each row begins with a new line).
     *
     * @return The formatted slot machine matrix as a String.
     */
    public String toString() {
        StringBuilder reelMatrixForPrint = new StringBuilder();
        for (int i = 0; i < numberOfRows; i++) {
            reelMatrixForPrint.append(reel1SelectedElements.get(i))
                    .append(" ")
                    .append(reel2SelectedElements.get(i))
                    .append(" ")
                    .append(reel3SelectedElements.get(i))
                    .append("\n");
        }

        return reelMatrixForPrint.toString();
    }

}
