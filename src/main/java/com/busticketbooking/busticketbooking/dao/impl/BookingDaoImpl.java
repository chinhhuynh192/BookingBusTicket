package com.busticketbooking.busticketbooking.dao.impl;

import com.busticketbooking.busticketbooking.dao.BookingDao;
import com.busticketbooking.busticketbooking.models.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImpl implements BookingDao {

    @Override
    public List<Booking> getAllBookingByTripId(int tripId) throws SQLException {
        List<Booking> bookingList = new ArrayList<Booking>();
        String sqlQuery = "SELECT [booking_id]\n" +
                "      ,[user_id]\n" +
                "      ,[trip_id]\n" +
                "      ,[date_booking]\n" +
                "      ,[seat_number]\n" +
                "      ,[price]\n" +
                "      ,[discount]\n" +
                "      ,[booking_status]\n" +
                "  FROM [dbo].[Booking]\n" +
                "  WHERE [trip_id] = ? AND [booking_status] IN ('WaitingPayment', 'Ok')";
        try(Connection connection = DBContext.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, tripId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int bookingId = rs.getInt(1);
                int userId = rs.getInt(2);
                int tripIdGetFromDb = rs.getInt(3);
                Date dateBooking = rs.getDate(4);
                String seatNumber = rs.getString(5);
                float price = rs.getFloat(6);
                float discount = rs.getFloat(7);
                String bookingStatus = rs.getString(8);
                Booking booking = new Booking(bookingId, userId, tripId, dateBooking, seatNumber, price, discount, bookingStatus);
                bookingList.add(booking);
            }
        }
        return bookingList;
    }

    @Override
    public List<Booking> getAllBookingHaveSeatNumber(int tripId, String seatNumberProvide) throws SQLException {
        List<Booking> bookingList = new ArrayList<Booking>();
        String sqlQuery = "SELECT [booking_id], [user_id], [trip_id], [date_booking], [seat_number], [price], [discount], [booking_status]\n" +
                "FROM [Booking]\n" +
                "WHERE EXISTS (\n" +
                "    SELECT 1\n" +
                "    FROM STRING_SPLIT(?, ',') AS input_seat\n" +
                "    WHERE CHARINDEX(input_seat.value, seat_number) > 0\n" +
                ") AND ([booking_status] IN ('WaitingPayment','Ok') ) AND trip_id = ?";
        try(Connection connection = DBContext.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, seatNumberProvide);
            statement.setInt(2, tripId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int bookingId = rs.getInt(1);
                int userId = rs.getInt(2);
                int tripIdGetFromDb = rs.getInt(3);
                Date dateBooking = rs.getDate(4);
                String seatNumber = rs.getString(5);
                float price = rs.getFloat(6);
                float discount = rs.getFloat(7);
                String bookingStatus = rs.getString(8);
                Booking booking = new Booking(bookingId, userId, tripId, dateBooking, seatNumber, price, discount, bookingStatus);
                bookingList.add(booking);
            }
        }
        return bookingList;
    }

    @Override
    public boolean insert(Booking booking) throws SQLException {
        String sqlQuery  = "INSERT INTO [dbo].[Booking]\n" +
                "           ([booking_id]\n" +
                "           ,[trip_id]\n" +
                "           ,[date_booking]\n" +
                "           ,[seat_number]\n" +
                "           ,[price]\n" +
                "           ,[discount]\n" +
                "           ,[booking_status], [user_id])\n" +
                "     VALUES\n" +
                "           (?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DBContext.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, booking.getBookingId());
            statement.setInt(2, booking.getTripId());
            statement.setDate(3, booking.getDateBooking());
            statement.setString(4, booking.getSeatNumber());
            statement.setFloat(5, booking.getPrice());
            statement.setFloat(6, booking.getDiscount());
            statement.setString(7, booking.getBookingStatus());
            statement.setInt(8, booking.getUserId());
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public Booking getById(int bookingId) throws SQLException{
        String sqlQuery = "SELECT [booking_id]\n" +
                "      ,[user_id]\n" +
                "      ,[trip_id]\n" +
                "      ,[date_booking]\n" +
                "      ,[seat_number]\n" +
                "      ,[price]\n" +
                "      ,[discount]\n" +
                "      ,[booking_status]\n" +
                "  FROM [BusTicketBooking].[dbo].[Booking]\n" +
                "  WHERE [booking_id] = ?";
        try(Connection connection = DBContext.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, bookingId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                int bookingIdFromDb = rs.getInt(1);
                int userId = rs.getInt(2);
                int tripId = rs.getInt(3);
                Date dateBooking = rs.getDate(4);
                String seatNumber = rs.getString(5);
                float price = rs.getFloat(6);
                float discount = rs.getFloat(7);
                String bookingStatus = rs.getString(8);
                return new Booking(bookingIdFromDb, userId, tripId, dateBooking, seatNumber, price, discount, bookingStatus);
            }
        }
        return null;
    }

    @Override
    public int getLastId() throws SQLException {
        String sqlQuery = "SELECT TOP (1) [booking_id]\n" +
                "  FROM [BusTicketBooking].[dbo].[Booking]\n" +
                "  ORDER BY [booking_id] DESC";
        try(Connection connection = DBContext.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("booking_id");
            }
        }
        return 0;
    }

    @Override
    public boolean update(Booking booking) throws SQLException {
        String sqlQuery = "UPDATE [dbo].[Booking]\n" +
                "   SET [booking_status] = ?\n" +
                " WHERE [booking_id] = ?";
        try(Connection connection = DBContext.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, booking.getBookingStatus());
            statement.setInt(2, booking.getBookingId());
            return statement.executeUpdate() > 0;
        }

    }
}
