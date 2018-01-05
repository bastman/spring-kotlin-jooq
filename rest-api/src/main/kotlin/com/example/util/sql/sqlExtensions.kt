package com.example.util.sql

import java.sql.Timestamp
import java.time.Instant


fun Instant.toSqlTimestamp() = Timestamp(this.toEpochMilli())
