const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const db = require("./db"); // Mengambil koneksi SQLite dari db.js

const app = express();
const PORT = 3000;

app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// 1. GET: Mengambil semua data mahasiswa
app.get("/mahasiswa", (req, res) => {
    const sql = "SELECT * FROM mahasiswa ORDER BY id DESC";
    db.all(sql, [], (err, rows) => {
        if (err) {
            return res.status(500).json({ success: false, error: err.message });
        }
        res.json({
            success: true,
            data: rows
        });
    });
});

// 2. POST: Menyimpan data mahasiswa baru
app.post("/mahasiswa", (req, res) => {
    const { nim, nama, prodi, semester, email } = req.body;

    if (!nim || !nama || !prodi || !semester || !email) {
        return res.status(400).json({ success: false, message: "Semua kolom wajib diisi!" });
    }

    const sql = "INSERT INTO mahasiswa (nim, nama, prodi, semester, email) VALUES (?, ?, ?, ?, ?)";
    const values = [nim, nama, prodi, semester, email];

    db.run(sql, values, function(err) {
        if (err) {
            if (err.message.includes('UNIQUE constraint failed')) {
                return res.status(400).json({ success: false, message: "NIM sudah terdaftar!" });
            }
            return res.status(500).json({ success: false, error: err.message });
        }
        res.status(201).json({
            success: true,
            message: "Data berhasil disimpan!"
        });
    });
});

app.listen(PORT, () => {
    console.log(`Server pusat layanan API berjalan di http://localhost:${PORT}`);
});