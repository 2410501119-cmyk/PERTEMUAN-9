const sqlite3 = require('sqlite3').verbose();
const path = require('path');

// Otomatis membuat file database lokal bernama 'akademik.db'
const dbPath = path.join(__dirname, 'akademik.db');
const db = new sqlite3.Database(dbPath, (err) => {
    if (err) {
        console.log("Koneksi database gagal: " + err.message);
    } else {
        console.log("Koneksi database (SQLite) berhasil!");
    }
});

// Otomatis membuat struktur tabel mahasiswa jika belum ada
db.serialize(() => {
    db.run(`CREATE TABLE IF NOT EXISTS mahasiswa (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nim TEXT UNIQUE NOT NULL,
        nama TEXT NOT NULL,
        prodi TEXT NOT NULL,
        semester INTEGER NOT NULL,
        email TEXT NOT NULL
    )`);
});

module.exports = db;