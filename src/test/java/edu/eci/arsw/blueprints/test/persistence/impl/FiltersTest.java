package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filter.BluePrintFilter;
import edu.eci.arsw.blueprints.filter.RedundancyFilter;
import edu.eci.arsw.blueprints.filter.SubsamplingFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class FiltersTest {

    @Test
    public void testRedundancyFilterRemovesConsecutiveDuplicates() {
        // Plano con puntos repetidos consecutivos
        Point[] pts = {
                new Point(10, 10),
                new Point(10, 10),
                new Point(20, 20),
                new Point(20, 20),
                new Point(30, 30)
        };
        Blueprint bp = new Blueprint("juan", "planoDuplicado", pts);

        BluePrintFilter filter = new RedundancyFilter();
        Blueprint filtered = filter.filter(bp);

        // Esperamos que se eliminen los consecutivos duplicados
        assertEquals(3, filtered.getPoints().size());
        assertEquals(new Point(10, 10), filtered.getPoints().get(0));
        assertEquals(new Point(20, 20), filtered.getPoints().get(1));
        assertEquals(new Point(30, 30), filtered.getPoints().get(2));
    }

    @Test
    public void testSubsamplingFilterRemovesEveryOtherPoint() {
        // Plano con 5 puntos
        Point[] pts = {
                new Point(0, 0),
                new Point(10, 10),
                new Point(20, 20),
                new Point(30, 30),
                new Point(40, 40)
        };
        Blueprint bp = new Blueprint("maria", "planoSubmuestreo", pts);

        BluePrintFilter filter = new SubsamplingFilter();
        Blueprint filtered = filter.filter(bp);

        // Esperamos que queden sólo los puntos en posiciones pares (0,2,4)
        assertEquals(3, filtered.getPoints().size());
        assertEquals(new Point(0, 0), filtered.getPoints().get(0));
        assertEquals(new Point(20, 20), filtered.getPoints().get(1));
        assertEquals(new Point(40, 40), filtered.getPoints().get(2));
    }
}
