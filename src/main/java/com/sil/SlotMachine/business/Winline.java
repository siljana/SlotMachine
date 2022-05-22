package com.sil.SlotMachine.business;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;

@Slf4j
@Getter
@Setter
@ToString
@Schema(description = "An occurrence of 3 same symbols starting on reel1 in a horizontal or diagonal alignment. " +
        "A combination from horizontal and diagonal alignment of the elements in the winline is also considered a " +
        "winline. The winline object contains the coordinates of the winning element from each reel " +
        "and the winning element symbol.")
public class Winline implements Serializable {

    private int winlineReel1Coordinates;
    private int winlineReel2Coordinates;
    private int winlineReel3Coordinates;
    private String winlineWinningElement;

    /**
     * A winline is an occurrence of 3 same symbols starting on reel1 in a horizontal or diagonal alignment. A combination
     * from horizontal and diagonal alignment of the elements in the winline is also considered a winline.
     * The winline object consists of the coordinates of the winning element from each reel and the element symbol itself.
     */
    public Winline(int reel1ElementIndex, int reel2ElementIndex, int reel3ElementIndex, String elementValue) {
        this.winlineReel1Coordinates = reel1ElementIndex;
        this.winlineReel2Coordinates = reel2ElementIndex;
        this.winlineReel3Coordinates = reel3ElementIndex;
        this.winlineWinningElement = elementValue;
    }

    public String toString() {
        StringBuilder winlineForPrint = new StringBuilder();
        winlineForPrint.append("[(")
                .append(this.winlineReel1Coordinates)
                .append(", ")
                .append(this.winlineReel2Coordinates)
                .append(", ")
                .append(this.winlineReel3Coordinates)
                .append("), ")
                .append(this.winlineWinningElement)
                .append("]");

        return winlineForPrint.toString();
    }


    @Override
    public boolean equals(Object winline) {
        if (winline instanceof Winline) {
            return this.getWinlineReel1Coordinates() == ((Winline) winline).getWinlineReel1Coordinates() &&
                    this.getWinlineReel2Coordinates() == ((Winline) winline).getWinlineReel2Coordinates() &&
                    this.getWinlineReel3Coordinates() == ((Winline) winline).getWinlineReel3Coordinates() &&
                    this.getWinlineWinningElement().equals(((Winline) winline).getWinlineWinningElement());
        } else {
            return false;
        }
    }

}
