package com.hu.training.stocktest;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.hu.training.stocktest.inventory.InventoryImpl;


public class InventoryTest {

	InventoryImpl inv;
	
	@Before
	public void setUp() throws Exception {
		inv = new InventoryImpl();
	}

	@Test
	public void testBondCountStringString() {
		assertEquals(11030, inv.bondCount("A1", "Bond1"));
		assertEquals(5500, inv.bondCount("A1", "Bond2"));
		assertEquals(2100, inv.bondCount("A1", "Bond3"));
		assertEquals(0, inv.bondCount("A1", "Bond4"));
		assertEquals(76030, inv.bondCount("A2", "Bond1"));
		assertEquals(7500, inv.bondCount("A2", "Bond2"));
		assertEquals(2100, inv.bondCount("A2", "Bond3"));
		assertEquals(0, inv.bondCount("A2", "Bond4"));
		assertEquals(109010, inv.bondCount("A3", "Bond1"));
		assertEquals(44600, inv.bondCount("A3", "Bond2"));
		assertEquals(0, inv.bondCount("A3", "Bond3"));
		assertEquals(205, inv.bondCount("A3", "Bond4"));
		assertEquals(43110, inv.bondCount("A4", "Bond1"));
		assertEquals(25340, inv.bondCount("A4", "Bond2"));
		assertEquals(97660, inv.bondCount("A4", "Bond3"));
		assertEquals(240, inv.bondCount("A4", "Bond4"));
		assertEquals(230000, inv.bondCount("A5", "Bond1"));
		assertEquals(60000, inv.bondCount("A5", "Bond2"));
		assertEquals(0, inv.bondCount("A5", "Bond3"));
		assertEquals(440, inv.bondCount("A5", "Bond4"));
		
	}

	@Test
	public void testStockCountStringString() {
		assertEquals(100, inv.stockCount("A1", "Stock1"));
		assertEquals(10, inv.stockCount("A1", "Stock2"));
		assertEquals(1000, inv.stockCount("A1", "Stock3"));
		assertEquals(80, inv.stockCount("A1", "Stock4"));
		assertEquals(30200, inv.stockCount("A2", "Stock1"));
		assertEquals(0, inv.stockCount("A2", "Stock2"));
		assertEquals(1200, inv.stockCount("A2", "Stock3"));
		assertEquals(800, inv.stockCount("A2", "Stock4"));
		assertEquals(45400, inv.stockCount("A3", "Stock1"));
		assertEquals(0, inv.stockCount("A3", "Stock2"));
		assertEquals(68200, inv.stockCount("A3", "Stock3"));
		assertEquals(300, inv.stockCount("A3", "Stock4"));
		assertEquals(15400, inv.stockCount("A4", "Stock1"));
		assertEquals(500, inv.stockCount("A4", "Stock2"));
		assertEquals(38100, inv.stockCount("A4", "Stock3"));
		assertEquals(163200, inv.stockCount("A4", "Stock4"));
		assertEquals(40000, inv.stockCount("A5", "Stock1"));
		assertEquals(1000, inv.stockCount("A5", "Stock2"));
		assertEquals(0, inv.stockCount("A5", "Stock3"));
		assertEquals(0, inv.stockCount("A5", "Stock4"));
		
	}

	@Test
	public void testBondCountString() {
		assertEquals(469180, inv.bondCount("Bond1"));
		assertEquals(142940, inv.bondCount("Bond2"));
		assertEquals(101860, inv.bondCount("Bond3"));
		assertEquals(885, inv.bondCount("Bond4"));
	}

	@Test
	public void testStockCountString() {
		assertEquals(131100, inv.stockCount("Stock1"));
		assertEquals(1510, inv.stockCount("Stock2"));
		assertEquals(108500, inv.stockCount("Stock3"));
		assertEquals(164380, inv.stockCount("Stock4"));
	}

}
