package parking;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTest {

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */
        ParkingLot parkinglot = mock(ParkingLot.class);
        Car car = mock(Car.class);

        when(car.getName()).thenReturn("JimmyCar");
        when(parkinglot.getName()).thenReturn("PK 1");

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        Receipt receipt = inOrderParkingStrategy.createReceipt(parkinglot, car);

        verify(car, times(1)).getName();
        verify(parkinglot, times(1)).getName();

        assertEquals("JimmyCar", receipt.getCarName());
        assertEquals("PK 1", receipt.getParkingLotName());

	}

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("JimmyCar");
        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(car);

        verify(car, times(1)).getName();
        assertEquals("JimmyCar", receipt.getCarName());
        assertEquals("No Parking Lot", receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
        Car car = new Car("jimmy Car");

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = spyInOrderParkingStrategy.park(null, car);

        verify(spyInOrderParkingStrategy).createNoSpaceReceipt(car);
    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
        Car car = new Car("jimmy Car");
        ParkingLot parkingLot = new ParkingLot("PK 1", 100);

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = spyInOrderParkingStrategy.park(Arrays.asList(parkingLot), car);

        verify(spyInOrderParkingStrategy).createReceipt(parkingLot, car);
    }

    @Test
    public void test_Park_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
        Car car = new Car("jimmy Car");
        ParkingLot parkingLot = new ParkingLot("PK 1", 1);

        parkingLot.getParkedCars().add(new Car("anotherCar"));

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = spyInOrderParkingStrategy.park(Arrays.asList(parkingLot), car);

        verify(spyInOrderParkingStrategy, times(0)).createReceipt(parkingLot, car);
        verify(spyInOrderParkingStrategy).createNoSpaceReceipt(car);
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        Car car = new Car("jimmy Car");
        ParkingLot parkingLot1 = new ParkingLot("PK 1", 1);
        ParkingLot parkingLot2 = new ParkingLot("PK 2", 1);

        parkingLot1.getParkedCars().add(new Car("anotherCar"));

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = spyInOrderParkingStrategy.park(Arrays.asList(parkingLot1, parkingLot2), car);

        verify(spyInOrderParkingStrategy, times(0)).createReceipt(parkingLot1, car);
        verify(spyInOrderParkingStrategy).createReceipt(parkingLot2, car);

        assertEquals("jimmy Car", receipt.getCarName());
        assertEquals("PK 2", receipt.getParkingLotName());
    }


}
