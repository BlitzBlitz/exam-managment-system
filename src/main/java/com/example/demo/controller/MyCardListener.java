package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;

public interface MyCardListener {
    void onClickListener(String cardTitle) throws SQLException, IOException;
}
