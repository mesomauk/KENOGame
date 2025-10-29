import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;

class MyTest {
    @Test
    void testBettingsSelectNumber() {
        Bettings betting = new Bettings();
        betting.setNumSpots(4);
        ArrayList<Integer> expected = new ArrayList<>();

        betting.selectNumber(10);
        expected.add(10);
        assertEquals(1, betting.getNumSelectedNumbers(), "Should have 1 selected number");
        assertEquals(expected, betting.getSelectedNumbers(), "Selected numbers should contain [10]");

        betting.selectNumber(20);
        expected.add(20);
        assertEquals(2, betting.getNumSelectedNumbers(), "Should have 2 selected numbers");
        assertEquals(expected, betting.getSelectedNumbers(), "Selected numbers should contain [10, 20]");

        betting.selectNumber(30);
        expected.add(30);
        assertEquals(3, betting.getNumSelectedNumbers(), "Should have 3 selected numbers");
        assertEquals(expected, betting.getSelectedNumbers(), "Selected numbers should contain [10, 20, 30]");

        betting.selectNumber(40);
        expected.add(40);
        assertEquals(4, betting.getNumSelectedNumbers(), "Should have 4 selected numbers");
        assertEquals(expected, betting.getSelectedNumbers(), "Selected numbers should contain [10, 20, 30, 40]");

        betting.selectNumber(50);
        assertEquals(4, betting.getNumSelectedNumbers(), "Should have 4 selected numbers (maximum spots reached)");
        assertEquals(expected, betting.getSelectedNumbers(), "Selected numbers should contain [10, 20, 30, 40]");

        betting.selectNumber(40);
        expected.remove(3);
        assertEquals(3, betting.getNumSelectedNumbers(), "Should have 3 selected numbers (select again removes)");
        assertEquals(expected, betting.getSelectedNumbers(), "Selected numbers should contain [10, 20, 30]");

        betting.selectNumber(30);
        expected.remove(2);
        assertEquals(2, betting.getNumSelectedNumbers(), "Should have 3 selected numbers (select again removes)");
        assertEquals(expected, betting.getSelectedNumbers(), "Selected numbers should contain [10, 20]");
    }

    @Test
    void testBettingsRemoveNumber() {
        Bettings betting = new Bettings();
        betting.setNumSpots(4);
        ArrayList<Integer> expected = new ArrayList<>();

        betting.selectNumber(10);
        betting.selectNumber(20);
        betting.selectNumber(30);
        betting.selectNumber(40);

        expected.add(10);
        expected.add(20);
        expected.add(30);
        expected.add(40);

        betting.removeNumber(40);
        expected.remove(3);
        assertEquals(3, betting.getNumSelectedNumbers(), "Incorrect number of selected numbers after removal");
        assertEquals(expected, betting.getSelectedNumbers(), "Incorrect array of selected numbers after removal");

        betting.removeNumber(30);
        expected.remove(2);
        assertEquals(2, betting.getNumSelectedNumbers(), "Incorrect number of selected numbers after removal");
        assertEquals(expected, betting.getSelectedNumbers(), "Incorrect array of selected numbers after removal");

        betting.removeNumber(20);
        expected.remove(1);
        assertEquals(1, betting.getNumSelectedNumbers(), "Incorrect number of selected numbers after removal");
        assertEquals(expected, betting.getSelectedNumbers(), "Incorrect array of selected numbers after removal");

        betting.removeNumber(10);
        expected.remove(0);
        assertEquals(0, betting.getNumSelectedNumbers(), "Incorrect number of selected numbers after removal");
        assertEquals(expected, betting.getSelectedNumbers(), "Incorrect array of selected numbers after removal");
    }

    @Test
    void testBettingsClearNumbers() {
        Bettings betting = new Bettings();
        betting.setNumSpots(4);
        ArrayList<Integer> expected = new ArrayList<>();

        betting.selectNumber(10);
        betting.selectNumber(20);
        betting.selectNumber(30);
        betting.selectNumber(40);

        expected.add(10);
        expected.add(20);
        expected.add(30);
        expected.add(40);

        assertEquals(4, betting.getNumSelectedNumbers(), "Incorrect number of selected numbers before clear");
        assertEquals(expected, betting.getSelectedNumbers(), "Incorrect array of selected numbers before clear");

        betting.clearNumbers();
        expected.clear();

        assertEquals(0, betting.getNumSelectedNumbers(), "Incorrect number of selected numbers after clear");
        assertEquals(expected, betting.getSelectedNumbers(), "Incorrect array of selected numbers after clear");
    }

    @Test
    void testBettingsAutoSelect() {
        Bettings betting1 = new Bettings();
        betting1.setNumSpots(1);
        betting1.autoSelect();

        Bettings betting2 = new Bettings();
        betting2.setNumSpots(4);
        betting2.autoSelect();

        Bettings betting3 = new Bettings();
        betting3.setNumSpots(8);
        betting3.autoSelect();

        Bettings betting4 = new Bettings();
        betting4.setNumSpots(10);
        betting4.autoSelect();

        assertEquals(1, betting1.getNumSelectedNumbers(), "Incorrect number of selected numbers after auto select");
        assertEquals(4, betting2.getNumSelectedNumbers(), "Incorrect number of selected numbers after auto select");
        assertEquals(8, betting3.getNumSelectedNumbers(), "Incorrect number of selected numbers after auto select");
        assertEquals(10, betting4.getNumSelectedNumbers(), "Incorrect number of selected numbers after auto select");
    }

    @Test
    void testDrawingGetDrawNumbers() {
        Drawing drawing = new Drawing();
        drawing.drawNumbers();
        HashSet<Integer> unique1 = new HashSet<>(drawing.getDrawnNumbers());
        assertEquals(20, unique1.size(), "Drawn numbers should be unique");
        drawing.clearDrawnNumbers();

        drawing.drawNumbers();
        HashSet<Integer> unique2 = new HashSet<>(drawing.getDrawnNumbers());
        assertEquals(20, unique2.size(), "Drawn numbers should be unique");
        drawing.clearDrawnNumbers();

        drawing.drawNumbers();
        HashSet<Integer> unique3 = new HashSet<>(drawing.getDrawnNumbers());
        assertEquals(20, unique3.size(), "Drawn numbers should be unique");
        drawing.clearDrawnNumbers();
    }

    @Test
    void testDrawingGetNumDrawNumbers() {
        Drawing drawing = new Drawing();
        drawing.drawNumbers();
        assertEquals(20, drawing.getNumDrawnNumbers(), "Incorrect number of drawn numbers after drawing");
        drawing.clearDrawnNumbers();

        drawing.drawNumbers();
        assertEquals(20, drawing.getNumDrawnNumbers(), "Incorrect number of drawn numbers after drawing");
        drawing.clearDrawnNumbers();

        drawing.drawNumbers();
        assertEquals(20, drawing.getNumDrawnNumbers(), "Incorrect number of drawn numbers after drawing");
        drawing.clearDrawnNumbers();
    }

    @Test
    void testDrawingClearDrawnNumbers() {
        Drawing drawing = new Drawing();
        drawing.drawNumbers();
        drawing.clearDrawnNumbers();
        assertEquals(0, drawing.getNumDrawnNumbers(), "Incorrect number of drawn numbers after drawing");

        drawing.drawNumbers();
        drawing.clearDrawnNumbers();
        assertEquals(0, drawing.getNumDrawnNumbers(), "Incorrect number of drawn numbers after drawing");

        drawing.drawNumbers();
        drawing.clearDrawnNumbers();
        assertEquals(0, drawing.getNumDrawnNumbers(), "Incorrect number of drawn numbers after drawing");
    }

    @Test
    void testResultsFindMatches() {
        Results results = new Results();
        ArrayList<Integer> array1 = new ArrayList<>();
        ArrayList<Integer> array2 = new ArrayList<>();

        array1.add(10);
        array1.add(20);
        array1.add(30);
        array1.add(40);
        array1.add(50);

        array2.add(10);
        array2.add(20);
        array2.add(30);
        array2.add(40);
        array2.add(50);

        results.findMatches(array1, array2);
        assertEquals(5, results.getNumMatches(), "Incorrect number of matches after finding");
        assertEquals(array2, results.getMatchedNumbers(), "Incorrect array of matched numbers after finding");

        array2.remove(4);
        array2.remove(3);

        results.findMatches(array1, array2);
        assertEquals(3, results.getNumMatches(), "Incorrect number of matches after finding");
        assertEquals(array2, results.getMatchedNumbers(), "Incorrect array of matched numbers after finding");

        array2.clear();
        results.findMatches(array1, array2);
        assertEquals(0, results.getNumMatches(), "Incorrect number of matches after finding");
        assertEquals(array2, results.getMatchedNumbers(), "Incorrect array of matched numbers after finding");
    }

    @Test
    void testResultsCalculatePrizeMoney() {
        Results results = new Results();

        results.calculatePrizeMoney(1, 1);
        assertEquals(2, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(1, 0);
        assertEquals(0, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(4, 2);
        assertEquals(1, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(4, 3);
        assertEquals(5, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(4, 4);
        assertEquals(75, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(8, 4);
        assertEquals(2, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(8, 5);
        assertEquals(12, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(8, 6);
        assertEquals(50, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(8, 7);
        assertEquals(750, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(8, 8);
        assertEquals(10000, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(10, 0);
        assertEquals(5, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(10, 5);
        assertEquals(2, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(10, 6);
        assertEquals(15, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(10, 7);
        assertEquals(40, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(10, 8);
        assertEquals(450, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(10, 9);
        assertEquals(4250, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);

        results.calculatePrizeMoney(10, 10);
        assertEquals(100000, results.getPrizeMoney(), "Incorrect amount of prize money after calculation");
        results.setPrizeMoney(0);
    }

    @Test
    void testResultsUpdateResults_Spots1_Match1() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        draw.add(10);
        results.updateResults(1, bets, draw);

        assertEquals(1, results.getNumMatches(), "Incorrect number of matches for 1-spot game");
        assertEquals(2, results.getPrizeMoney(), "Incorrect amount of prize money 1-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots4_Match2() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(11);
        bets.add(22);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        results.updateResults(4, bets, draw);

        assertEquals(2, results.getNumMatches(), "Incorrect number of matches for 4-spot game");
        assertEquals(1, results.getPrizeMoney(), "Incorrect amount of prize money 4-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots4_Match3() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(22);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        results.updateResults(4, bets, draw);

        assertEquals(3, results.getNumMatches(), "Incorrect number of matches for 4-spot game");
        assertEquals(5, results.getPrizeMoney(), "Incorrect amount of prize money 4-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots4_Match4() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        results.updateResults(4, bets, draw);

        assertEquals(4, results.getNumMatches(), "Incorrect number of matches for 4-spot game");
        assertEquals(75, results.getPrizeMoney(), "Incorrect amount of prize money 4-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots8_Match4() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(11);
        bets.add(12);
        bets.add(13);
        bets.add(14);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        results.updateResults(8, bets, draw);

        assertEquals(4, results.getNumMatches(), "Incorrect number of matches for 8-spot game");
        assertEquals(2, results.getPrizeMoney(), "Incorrect amount of prize money 8-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots8_Match5() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(12);
        bets.add(13);
        bets.add(14);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        results.updateResults(8, bets, draw);

        assertEquals(5, results.getNumMatches(), "Incorrect number of matches for 8-spot game");
        assertEquals(12, results.getPrizeMoney(), "Incorrect amount of prize money 8-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots8_Match6() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(60);
        bets.add(13);
        bets.add(14);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        results.updateResults(8, bets, draw);

        assertEquals(6, results.getNumMatches(), "Incorrect number of matches for 8-spot game");
        assertEquals(50, results.getPrizeMoney(), "Incorrect amount of prize money 8-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots8_Match7() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(60);
        bets.add(70);
        bets.add(14);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        results.updateResults(8, bets, draw);

        assertEquals(7, results.getNumMatches(), "Incorrect number of matches for 8-spot game");
        assertEquals(750, results.getPrizeMoney(), "Incorrect amount of prize money 8-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots8_Match8() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(60);
        bets.add(70);
        bets.add(80);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        results.updateResults(8, bets, draw);

        assertEquals(8, results.getNumMatches(), "Incorrect number of matches for 8-spot game");
        assertEquals(10000, results.getPrizeMoney(), "Incorrect amount of prize money 8-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots10_Match0() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(11);
        bets.add(12);
        bets.add(13);
        bets.add(14);
        bets.add(15);
        bets.add(16);
        bets.add(17);
        bets.add(18);
        bets.add(19);
        bets.add(0);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        draw.add(90);
        draw.add(100);
        results.updateResults(10, bets, draw);

        assertEquals(0, results.getNumMatches(), "Incorrect number of matches for 10-spot game");
        assertEquals(5, results.getPrizeMoney(), "Incorrect amount of prize money 10-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots10_Match5() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(16);
        bets.add(17);
        bets.add(18);
        bets.add(19);
        bets.add(0);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        draw.add(90);
        draw.add(100);
        results.updateResults(10, bets, draw);

        assertEquals(5, results.getNumMatches(), "Incorrect number of matches for 10-spot game");
        assertEquals(2, results.getPrizeMoney(), "Incorrect amount of prize money 10-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots10_Match6() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(60);
        bets.add(17);
        bets.add(18);
        bets.add(19);
        bets.add(0);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        draw.add(90);
        draw.add(100);
        results.updateResults(10, bets, draw);

        assertEquals(6, results.getNumMatches(), "Incorrect number of matches for 10-spot game");
        assertEquals(15, results.getPrizeMoney(), "Incorrect amount of prize money 10-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots10_Match7() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(60);
        bets.add(70);
        bets.add(18);
        bets.add(19);
        bets.add(0);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        draw.add(90);
        draw.add(100);
        results.updateResults(10, bets, draw);

        assertEquals(7, results.getNumMatches(), "Incorrect number of matches for 10-spot game");
        assertEquals(40, results.getPrizeMoney(), "Incorrect amount of prize money 10-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots10_Match8() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(60);
        bets.add(70);
        bets.add(80);
        bets.add(19);
        bets.add(0);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        draw.add(90);
        draw.add(100);
        results.updateResults(10, bets, draw);

        assertEquals(8, results.getNumMatches(), "Incorrect number of matches for 10-spot game");
        assertEquals(450, results.getPrizeMoney(), "Incorrect amount of prize money 10-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots10_Match9() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(60);
        bets.add(70);
        bets.add(80);
        bets.add(90);
        bets.add(0);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        draw.add(90);
        draw.add(100);
        results.updateResults(10, bets, draw);

        assertEquals(9, results.getNumMatches(), "Incorrect number of matches for 10-spot game");
        assertEquals(4250, results.getPrizeMoney(), "Incorrect amount of prize money 10-spot game");
    }

    @Test
    void testResultsUpdateResults_Spots10_Match10() {
        Results results = new Results();
        ArrayList<Integer> bets = new ArrayList<>();
        ArrayList<Integer> draw = new ArrayList<>();

        bets.add(10);
        bets.add(20);
        bets.add(30);
        bets.add(40);
        bets.add(50);
        bets.add(60);
        bets.add(70);
        bets.add(80);
        bets.add(90);
        bets.add(100);
        draw.add(10);
        draw.add(20);
        draw.add(30);
        draw.add(40);
        draw.add(50);
        draw.add(60);
        draw.add(70);
        draw.add(80);
        draw.add(90);
        draw.add(100);
        results.updateResults(10, bets, draw);

        assertEquals(10, results.getNumMatches(), "Incorrect number of matches for 10-spot game");
        assertEquals(100000, results.getPrizeMoney(), "Incorrect amount of prize money 10-spot game");
    }

    @Test
    void testGameRunDrawing_1Spot_1PerfectDraw() {
        Bettings bettings = new Bettings();
        bettings.setNumSpots(1);
        bettings.selectNumber(1);

        Drawing drawing = new Drawing() {
            @Override
            public void drawNumbers() {
                getDrawnNumbers().clear();
                for (int i = 1; i <= 20; i++) {
                    getDrawnNumbers().add(i);
                }
            }
        };

        Game game = new Game(bettings, drawing);
        game.startGame(1);

        game.runDrawing();
        assertEquals(1, game.getDrawIndex(), "Draw index did not increment");
        assertEquals(1, game.getResults().size(), "Results size did not increment");

        Results result = game.getResults().get(0);
        assertEquals(1, result.getNumMatches(), "Incorrect number of matches");
        assertEquals(2, result.getPrizeMoney(), "Incorrect amount of prize money");
        assertEquals(2, game.getTotalWinnings(), "Incorrect amount of total winnings");
    }

    @Test
    void testGameRunDrawing_1Spot_4PerfectDraws() {
        Bettings bettings = new Bettings();
        bettings.setNumSpots(1);
        bettings.selectNumber(1);

        Drawing drawing = new Drawing() {
            @Override
            public void drawNumbers() {
                getDrawnNumbers().clear();
                for (int i = 1; i <= 20; i++) {
                    getDrawnNumbers().add(i);
                }
            }
        };

        Game game = new Game(bettings, drawing);
        game.startGame(4);

        for (int i = 0; i < 4; i++) {
            game.runDrawing();
            assertEquals(i + 1, game.getDrawIndex(), "Draw index incorrect");
            assertEquals(i + 1, game.getResults().size(), "Results size incorrect");

            Results result = game.getResults().get(i);
            assertEquals(1, result.getNumMatches(), "Incorrect number of matches");
            assertEquals(2, result.getPrizeMoney(), "Incorrect amount of prize money");
        }

        assertEquals(8, game.getTotalWinnings(), "Total winnings incorrect after 4 rounds");
    }

    @Test
    void testGameRunDrawing_4Spots_1PerfectDraw() {
        Bettings bettings = new Bettings();
        bettings.setNumSpots(4);
        bettings.selectNumber(1);
        bettings.selectNumber(2);
        bettings.selectNumber(3);
        bettings.selectNumber(4);

        Drawing drawing = new Drawing() {
            @Override
            public void drawNumbers() {
                getDrawnNumbers().clear();
                for (int i = 1; i <= 20; i++) {
                    getDrawnNumbers().add(i);
                }
            }
        };

        Game game = new Game(bettings, drawing);
        game.startGame(1);

        game.runDrawing();
        assertEquals(1, game.getDrawIndex(), "Draw index did not increment");
        assertEquals(1, game.getResults().size(), "Results size did not increment");

        Results result = game.getResults().get(0);
        assertEquals(4, result.getNumMatches(), "Incorrect number of matches");
        assertEquals(75, result.getPrizeMoney(), "Incorrect amount of prize money");
        assertEquals(75, game.getTotalWinnings(), "Incorrect amount of total winnings");
    }

    @Test
    void testGameRunDrawing_4Spots_4PerfectDraws() {
        Bettings bettings = new Bettings();
        bettings.setNumSpots(4);
        bettings.selectNumber(1);
        bettings.selectNumber(2);
        bettings.selectNumber(3);
        bettings.selectNumber(4);

        Drawing drawing = new Drawing() {
            @Override
            public void drawNumbers() {
                getDrawnNumbers().clear();
                for (int i = 1; i <= 20; i++) {
                    getDrawnNumbers().add(i);
                }
            }
        };

        Game game = new Game(bettings, drawing);
        game.startGame(4);

        for (int i = 0; i < 4; i++) {
            game.runDrawing();
            assertEquals(i + 1, game.getDrawIndex(), "Draw index incorrect");
            assertEquals(i + 1, game.getResults().size(), "Results size incorrect");

            Results result = game.getResults().get(i);
            assertEquals(4, result.getNumMatches(), "Incorrect number of matches");
            assertEquals(75, result.getPrizeMoney(), "Incorrect amount of prize money");
        }

        assertEquals(300, game.getTotalWinnings(), "Total winnings incorrect after 4 rounds");
    }

    @Test
    void testGameRunDrawing_8Spots_1PerfectDraw() {
        Bettings bettings = new Bettings();
        bettings.setNumSpots(8);
        bettings.selectNumber(1);
        bettings.selectNumber(2);
        bettings.selectNumber(3);
        bettings.selectNumber(4);
        bettings.selectNumber(5);
        bettings.selectNumber(6);
        bettings.selectNumber(7);
        bettings.selectNumber(8);

        Drawing drawing = new Drawing() {
            @Override
            public void drawNumbers() {
                getDrawnNumbers().clear();
                for (int i = 1; i <= 20; i++) {
                    getDrawnNumbers().add(i);
                }
            }
        };

        Game game = new Game(bettings, drawing);
        game.startGame(1);

        game.runDrawing();
        assertEquals(1, game.getDrawIndex(), "Draw index did not increment");
        assertEquals(1, game.getResults().size(), "Results size did not increment");

        Results result = game.getResults().get(0);
        assertEquals(8, result.getNumMatches(), "Incorrect number of matches");
        assertEquals(10000, result.getPrizeMoney(), "Incorrect amount of prize money");
        assertEquals(10000, game.getTotalWinnings(), "Incorrect amount of total winnings");
    }

    @Test
    void testGameRunDrawing_8Spots_4PerfectDraws() {
        Bettings bettings = new Bettings();
        bettings.setNumSpots(8);
        bettings.selectNumber(1);
        bettings.selectNumber(2);
        bettings.selectNumber(3);
        bettings.selectNumber(4);
        bettings.selectNumber(5);
        bettings.selectNumber(6);
        bettings.selectNumber(7);
        bettings.selectNumber(8);

        Drawing drawing = new Drawing() {
            @Override
            public void drawNumbers() {
                getDrawnNumbers().clear();
                for (int i = 1; i <= 20; i++) {
                    getDrawnNumbers().add(i);
                }
            }
        };

        Game game = new Game(bettings, drawing);
        game.startGame(4);

        for (int i = 0; i < 4; i++) {
            game.runDrawing();
            assertEquals(i + 1, game.getDrawIndex(), "Draw index incorrect");
            assertEquals(i + 1, game.getResults().size(), "Results size incorrect");

            Results result = game.getResults().get(i);
            assertEquals(8, result.getNumMatches(), "Incorrect number of matches");
            assertEquals(10000, result.getPrizeMoney(), "Incorrect amount of prize money");
        }

        assertEquals(40000, game.getTotalWinnings(), "Total winnings incorrect after 4 rounds");
    }

    @Test
    void testGameRunDrawing_10Spots_1PerfectDraw() {
        Bettings bettings = new Bettings();
        bettings.setNumSpots(10);
        bettings.selectNumber(1);
        bettings.selectNumber(2);
        bettings.selectNumber(3);
        bettings.selectNumber(4);
        bettings.selectNumber(5);
        bettings.selectNumber(6);
        bettings.selectNumber(7);
        bettings.selectNumber(8);
        bettings.selectNumber(9);
        bettings.selectNumber(10);

        Drawing drawing = new Drawing() {
            @Override
            public void drawNumbers() {
                getDrawnNumbers().clear();
                for (int i = 1; i <= 20; i++) {
                    getDrawnNumbers().add(i);
                }
            }
        };

        Game game = new Game(bettings, drawing);
        game.startGame(1);

        game.runDrawing();
        assertEquals(1, game.getDrawIndex(), "Draw index did not increment");
        assertEquals(1, game.getResults().size(), "Results size did not increment");

        Results result = game.getResults().get(0);
        assertEquals(10, result.getNumMatches(), "Incorrect number of matches");
        assertEquals(100000, result.getPrizeMoney(), "Incorrect amount of prize money");
        assertEquals(100000, game.getTotalWinnings(), "Incorrect amount of total winnings");
    }

    @Test
    void testGameRunDrawing_10Spots_4PerfectDraws() {
        Bettings bettings = new Bettings();
        bettings.setNumSpots(10);
        bettings.selectNumber(1);
        bettings.selectNumber(2);
        bettings.selectNumber(3);
        bettings.selectNumber(4);
        bettings.selectNumber(5);
        bettings.selectNumber(6);
        bettings.selectNumber(7);
        bettings.selectNumber(8);
        bettings.selectNumber(9);
        bettings.selectNumber(10);

        Drawing drawing = new Drawing() {
            @Override
            public void drawNumbers() {
                getDrawnNumbers().clear();
                for (int i = 1; i <= 20; i++) {
                    getDrawnNumbers().add(i);
                }
            }
        };

        Game game = new Game(bettings, drawing);
        game.startGame(4);

        for (int i = 0; i < 4; i++) {
            game.runDrawing();
            assertEquals(i + 1, game.getDrawIndex(), "Draw index incorrect");
            assertEquals(i + 1, game.getResults().size(), "Results size incorrect");

            Results result = game.getResults().get(i);
            assertEquals(10, result.getNumMatches(), "Incorrect number of matches");
            assertEquals(100000, result.getPrizeMoney(), "Incorrect amount of prize money");
        }

        assertEquals(400000, game.getTotalWinnings(), "Total winnings incorrect after 4 rounds");
    }

    @Test
    void testGameUpdateWinnings() {
        Game game = new Game(new Bettings(), new Drawing());
        game.updateWinnings(100);
        assertEquals(100, game.getTotalWinnings(), "Incorrect total amount of winnings");

        game.updateWinnings(100);
        assertEquals(200, game.getTotalWinnings(), "Incorrect total amount of winnings");

        game.updateWinnings(100);
        assertEquals(300, game.getTotalWinnings(), "Incorrect total amount of winnings");
    }

    @Test
    void testGameGetCurrRound() {
        Game game = new Game(new Bettings(), new Drawing());
        game.startGame(4);
        game.runDrawing();
        assertEquals(1, game.getCurrRound(), "Current round incorrect");

        game.runDrawing();
        assertEquals(2, game.getCurrRound(), "Current round incorrect");

        game.runDrawing();
        assertEquals(3, game.getCurrRound(), "Current round incorrect");

        game.runDrawing();
        assertEquals(4, game.getCurrRound(), "Current round incorrect");
    }
}
