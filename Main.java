
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    static ArrayList<Menu> daftarMenu = new ArrayList<>();

    public static void main(String[] args) {

        daftarMenu.add(new Menu("Nasi Kebuli", 30000, "Makanan"));
        daftarMenu.add(new Menu("Bakso", 15000, "Makanan"));
        daftarMenu.add(new Menu("Ayam Panggang", 45000, "Makanan"));
        daftarMenu.add(new Menu("Ikan Bakar", 50000, "Makanan"));

        daftarMenu.add(new Menu("Es Teh", 5000, "Minuman"));
        daftarMenu.add(new Menu("Teh Tarik", 10000, "Minuman"));
        daftarMenu.add(new Menu("Air Mineral", 8000, "Minuman"));
        daftarMenu.add(new Menu("Es Jeruk", 9000, "Minuman"));

        menuUtama();
    }

    static void menuUtama() {

        while (true) {
            System.out.println("\n================================");
            System.out.println("       APLIKASI RESTORAN");
            System.out.println("================================");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Menu Pengelolaan Restoran");
            System.out.println("3. Keluar");
            System.out.println("================================");
            System.out.print("Pilih menu: ");

            int pilihan = bacaAngka();

            switch (pilihan) {
                case 1:
                    menuPelanggan();
                    break;

                case 2:
                    menuPengelolaan();
                    break;

                case 3:
                    System.out.println("Terima kasih telah menggunakan aplikasi!");
                    return;

                default:
                    System.out.println("Pilihan tidak tersedia!");
            }
        }
    }

    static void menuPelanggan() {

        LinkedHashMap<Menu, Integer> pesanan = new LinkedHashMap<>();

        System.out.println("\n================================");
        System.out.println("         DAFTAR MENU");
        System.out.println("================================");

        tampilkanMenu();

        System.out.println("\nKetik nomor menu untuk memesan.");
        System.out.println("Ketik 'selesai' jika pesanan sudah selesai.");

        while (true) {

            System.out.print("\nMasukkan pilihan menu: ");
            String pilihan = input.nextLine().trim();

            if (pilihan.equalsIgnoreCase("selesai")) {
                break;
            }

            int nomor;

            try {
                nomor = Integer.parseInt(pilihan);
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid! Silakan pilih nomor menu.");
                continue;
            }

            if (nomor < 1 || nomor > daftarMenu.size()) {
                System.out.println("Menu tidak tersedia! Silakan pilih kembali.");
                continue;
            }

            Menu menuDipilih = daftarMenu.get(nomor - 1);

            System.out.println("Menu dipilih: " + menuDipilih.getNama());

            System.out.print("Masukkan jumlah pesanan: ");
            int jumlah = bacaAngka();

            if (jumlah <= 0) {
                System.out.println("Jumlah pesanan harus lebih dari 0!");
                continue;
            }

            pesanan.put(
                menuDipilih,
                pesanan.getOrDefault(menuDipilih, 0) + jumlah
            );

            System.out.println("Pesanan berhasil ditambahkan!");
        }

        if (pesanan.isEmpty()) {
            System.out.println("Tidak ada pesanan.");
            return;
        }

        cetakStruk(pesanan);
    }


    static void tampilkanMenu() {

        System.out.println("\n--------------- MAKANAN ---------------");

        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu m = daftarMenu.get(i);

            if (m.getKatagori().equalsIgnoreCase("Makanan")) {
                System.out.printf("%d. %-20s Rp %,d%n",
                    i + 1, m.getNama(), m.getHarga());
            }
        }

        System.out.println("\n--------------- MINUMAN ---------------");

        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu m = daftarMenu.get(i);

            if (m.getKatagori().equalsIgnoreCase("Minuman")) {
                System.out.printf("%d. %-20s Rp %,d%n",
                    i + 1, m.getNama(), m.getHarga());
            }
        }
    }


    static void cetakStruk(LinkedHashMap<Menu, Integer> pesanan) {

        int subtotal = 0;
        int diskonMinuman = 0;

        System.out.println("\n==============================================");
        System.out.println("              STRUK PEMBAYARAN");
        System.out.println("==============================================");

        System.out.printf("%-18s %5s %12s %12s%n",
            "Nama Menu", "Qty", "Harga", "Total");

        System.out.println("----------------------------------------------");

        for (Map.Entry<Menu, Integer> data : pesanan.entrySet()) {

            Menu menu = data.getKey();
            int jumlah = data.getValue();

            int total = menu.getHarga() * jumlah;
            subtotal += total;

            System.out.printf("%-18s %5d %,12d %,12d%n",
                menu.getNama(),
                jumlah,
                menu.getHarga(),
                total);
        }

        System.out.println("----------------------------------------------");

        System.out.printf("%-25s Rp %,d%n", "Subtotal:", subtotal);

        if (subtotal > 50000) {

            for (Map.Entry<Menu, Integer> data : pesanan.entrySet()) {

                Menu menu = data.getKey();
                int jumlah = data.getValue();

                if (menu.getKatagori().equalsIgnoreCase("Minuman")) {

                    int gratis = jumlah / 2;

                    diskonMinuman += gratis * menu.getHarga();
                }
            }
        }

        if (diskonMinuman > 0) {
            System.out.printf("Diskon Beli 1 Gratis 1: Rp %,d%n",
                diskonMinuman);
        }

        int diskon10 = 0;

        if (subtotal > 100000) {
            diskon10 = (int) (subtotal * 0.10);

            System.out.printf("Diskon 10%%: Rp %,d%n", diskon10);
        }

        int totalDiskon = diskonMinuman + diskon10;
        int totalSetelahDiskon = subtotal - totalDiskon;
        int pajak = (int) (totalSetelahDiskon * 0.10);
        int biayaPelayanan = 20000;
        int totalBayar = totalSetelahDiskon + pajak + biayaPelayanan;

        System.out.println("----------------------------------------------");

        System.out.printf("%-25s Rp %,d%n",
            "Total setelah diskon:", totalSetelahDiskon);

        System.out.printf("%-25s Rp %,d%n",
            "Pajak 10%:", pajak);

        System.out.printf("%-25s Rp %,d%n",
            "Biaya pelayanan:", biayaPelayanan);

        System.out.println("==============================================");

        System.out.printf("%-25s Rp %,d%n",
            "TOTAL PEMBAYARAN:", totalBayar);

        System.out.println("==============================================");
        System.out.println("       TERIMA KASIH ATAS PESANAN ANDA!");
        System.out.println("==============================================");
    }

    static void menuPengelolaan() {

        while (true) {

            System.out.println("\n================================");
            System.out.println("    PENGELOLAAN MENU RESTORAN");
            System.out.println("================================");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Tampilkan Daftar Menu");
            System.out.println("5. Kembali ke Menu Utama");
            System.out.println("================================");
            System.out.print("Pilih menu: ");

            int pilihan = bacaAngka();

            switch (pilihan) {

                case 1:
                    tambahMenu();
                    break;

                case 2:
                    ubahMenu();
                    break;

                case 3:
                    hapusMenu();
                    break;

                case 4:
                    tampilkanMenu();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Pilihan tidak tersedia!");
            }
        }
    }

    static void tambahMenu() {

        System.out.println("\n========== TAMBAH MENU ==========");

        System.out.print("Masukkan nama menu: ");
        String nama = input.nextLine().trim();

        if (nama.isEmpty()) {
            System.out.println("Nama menu tidak boleh kosong!");
            return;
        }

        System.out.print("Masukkan harga menu: ");
        int harga = bacaAngka();

        if (harga <= 0) {
            System.out.println("Harga harus lebih dari 0!");
            return;
        }

        System.out.print("Masukkan kategori (Makanan/Minuman): ");
        String kategori = input.nextLine().trim();

        if (!kategori.equalsIgnoreCase("Makanan")
                && !kategori.equalsIgnoreCase("Minuman")) {

            System.out.println("Kategori tidak valid!");
            return;
        }

        kategori = kategori.substring(0, 1).toUpperCase()
                + kategori.substring(1).toLowerCase();

        System.out.print("Yakin ingin menambahkan menu? (Ya/Tidak): ");
        String konfirmasi = input.nextLine().trim();

        if (konfirmasi.equalsIgnoreCase("Ya")) {

            daftarMenu.add(new Menu(nama, harga, kategori));

            System.out.println("Menu berhasil ditambahkan!");

        } else {
            System.out.println("Penambahan menu dibatalkan.");
        }
    }

    static void ubahMenu() {

        System.out.println("\n========== UBAH HARGA MENU ==========");

        tampilkanMenu();

        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu.");
            return;
        }

        System.out.print("\nMasukkan nomor menu yang ingin diubah: ");
        int nomor = bacaAngka();

        if (nomor < 1 || nomor > daftarMenu.size()) {
            System.out.println("Nomor menu tidak tersedia!");
            return;
        }

        Menu menu = daftarMenu.get(nomor - 1);

        System.out.println("Nama menu  : " + menu.getNama());
        System.out.println("Harga lama : Rp " + menu.getHarga());

        System.out.print("Masukkan harga baru: ");
        int hargaBaru = bacaAngka();

        if (hargaBaru <= 0) {
            System.out.println("Harga harus lebih dari 0!");
            return;
        }

        System.out.print("Yakin ingin mengubah harga? (Ya/Tidak): ");
        String konfirmasi = input.nextLine().trim();

        if (konfirmasi.equalsIgnoreCase("Ya")) {

            menu.setHarga(hargaBaru);

            System.out.println("Harga menu berhasil diubah!");

        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    static void hapusMenu() {

        System.out.println("\n========== HAPUS MENU ==========");

        tampilkanMenu();

        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu.");
            return;
        }

        System.out.print("\nMasukkan nomor menu yang ingin dihapus: ");
        int nomor = bacaAngka();

        if (nomor < 1 || nomor > daftarMenu.size()) {
            System.out.println("Nomor menu tidak tersedia!");
            return;
        }

        Menu menu = daftarMenu.get(nomor - 1);

        System.out.println("Menu yang akan dihapus: " + menu.getNama());

        System.out.print("Yakin ingin menghapus menu? (Ya/Tidak): ");
        String konfirmasi = input.nextLine().trim();

        if (konfirmasi.equalsIgnoreCase("Ya")) {

            daftarMenu.remove(nomor - 1);

            System.out.println("Menu berhasil dihapus!");

        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    static int bacaAngka() {

        while (true) {

            String nilai = input.nextLine().trim();

            try {
                return Integer.parseInt(nilai);

            } catch (NumberFormatException e) {

                System.out.print("Masukkan angka yang valid: ");
            }
        }
    }
}

