public class MenuItem {
    private String nama;
    private double harga;
    private String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void tampilMenu() {
        System.out.println("Nama: " + nama);
        System.out.println("Harga: Rp" + String.format("%.0f", harga));
        System.out.println("Kategori: " + kategori);
    }
}


public class Makanan extends MenuItem {

    public Makanan(String nama, double harga) {
        super(nama, harga, "Makanan");
    }

    @Override
    public void tampilMenu() {
        System.out.println("Nama: " + getNama());
        System.out.println("Harga: Rp" + String.format("%.0f", getHarga()));
        System.out.println("Kategori: " + getKategori());
    }
}


public class Minuman extends MenuItem {

    public Minuman(String nama, double harga) {
        super(nama, harga, "Minuman");
    }

    @Override
    public void tampilMenu() {
        System.out.println("Nama: " + getNama());
        System.out.println("Harga: Rp" + String.format("%.0f", getHarga()));
        System.out.println("Kategori: " + getKategori());
    }
}


public class Diskon extends MenuItem {

    private double diskon;

    public Diskon(String nama, double diskon) {
        super(nama, 0, "Diskon");
        this.diskon = diskon;
    }

    public double getDiskon() {
        return diskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Nama: " + getNama());
        System.out.println("Diskon: " + diskon + "%");
    }
}


import java.util.*;
import java.text.DecimalFormat;

public class Pesanan {

    private String pelanggan;
    private ArrayList<PesananItem> daftarPesanan;

    private final double PAJAK_TRANSAKSI = 20000;
    private final double BIAYA_PELAYANAN = 10000;

    public Pesanan(String pelanggan) {
        this.pelanggan = pelanggan;
        daftarPesanan = new ArrayList<>();
    }

    public void tambahPesanan(PesananItem item) {
        daftarPesanan.add(item);
    }

    public void tampilkanPesanan() {

        DecimalFormat format = new DecimalFormat("#,##0");

        double totalBelanja = 0;
        int totalMinuman = 0;

        System.out.println("\n==================================");
        System.out.println("           STRUK PESANAN");
        System.out.println("==================================");

        System.out.println("Nama Pelanggan: " + pelanggan);

        System.out.println("----------------------------------");

        for (PesananItem pesanan : daftarPesanan) {

            MenuItem item = pesanan.getItem();
            int jumlah = pesanan.getJumlah();

            double subtotal = pesanan.getSubtotal();

            totalBelanja += subtotal;

            if (item instanceof Minuman) {
                totalMinuman += jumlah;
            }

            System.out.println(item.getNama());

            System.out.println(
                jumlah + " x Rp" + format.format(item.getHarga())
                + " = Rp" + format.format(subtotal)
            );
        }

        double diskon = 0;

        if (totalBelanja > 100000) {
            diskon = totalBelanja * 0.10;
        }

        int minumanGratis = totalMinuman / 2;

        double totalTambahan =
            PAJAK_TRANSAKSI +
            BIAYA_PELAYANAN;

        double totalAkhir =
            totalBelanja
            - diskon
            + totalTambahan;

        System.out.println("----------------------------------");

        System.out.println(
            "Total Belanja       : Rp" + format.format(totalBelanja)
        );

        System.out.println(
            "Diskon 10%          : Rp" + format.format(diskon)
        );

        System.out.println(
            "Minuman Gratis      : " + minumanGratis
        );

        System.out.println(
            "Pajak Transaksi     : Rp" + format.format(PAJAK_TRANSAKSI)
        );

        System.out.println(
            "Biaya Pelayanan     : Rp" + format.format(BIAYA_PELAYANAN)
        );

        System.out.println("----------------------------------");

        System.out.println(
            "TOTAL BAYAR         : Rp" + format.format(totalAkhir)
        );

        System.out.println("==================================");
        System.out.println("       TERIMA KASIH!");
        System.out.println("==================================");
    }
}


import java.util.ArrayList;

public class Menu {

    private ArrayList<MenuItem> menuList =
        new ArrayList<>();

    public void tambahMenu(MenuItem item) {
        menuList.add(item);
    }

    public ArrayList<MenuItem> getMenuList() {
        return menuList;
    }

    public void tampilMenu() {

        System.out.println("\n================================");
        System.out.println("          DAFTAR MENU");
        System.out.println("================================");

        if (menuList.isEmpty()) {
            System.out.println("Menu masih kosong!");
            return;
        }

        for (int i = 0; i < menuList.size(); i++) {

            System.out.println("\nNomor Menu: " + (i + 1));

            MenuItem item = menuList.get(i);

            item.tampilMenu();

            System.out.println("--------------------------------");
        }
    }
}


public class PesananItem {

    private MenuItem item;
    private int jumlah;

    public PesananItem(MenuItem item, int jumlah) {
        this.item = item;
        this.jumlah = jumlah;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getSubtotal() {
        return item.getHarga() * jumlah;
    }
}


public class MenuItem {
    private String nama;
    private double harga;
    private String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void tampilMenu() {
        System.out.println("Nama: " + nama);
        System.out.println("Harga: Rp" + String.format("%.0f", harga));
        System.out.println("Kategori: " + kategori);
    }
}


import java.io.*;
import java.util.*;

public class Main {

    private static Menu menu = new Menu();
    private static Pesanan pesanan = null;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        loadMenuFromFile();

        if (menu.getMenuList().isEmpty()) {
            tambahMenuDefault();
        }

        while (true) {

            System.out.println("\n================================");
            System.out.println("       APLIKASI RESTORAN");
            System.out.println("================================");
            System.out.println("1. Tambah Item Menu");
            System.out.println("2. Tampilkan Menu");
            System.out.println("3. Buat Pesanan");
            System.out.println("4. Tampilkan Struk Pesanan");
            System.out.println("5. Simpan Menu ke File");
            System.out.println("6. Keluar");
            System.out.println("================================");
            System.out.print("Pilih menu: ");

            int pilih = bacaInteger(input);

            switch (pilih) {

                case 1:
                    tambahItemMenu(input);
                    break;

                case 2:
                    menu.tampilMenu();
                    break;

                case 3:
                    buatPesanan(input);
                    break;

                case 4:
                    if (pesanan != null) {
                        pesanan.tampilkanPesanan();
                        saveStrukToFile();
                    } else {
                        System.out.println("Belum ada pesanan!");
                    }
                    break;

                case 5:
                    saveMenuToFile();
                    break;

                case 6:
                    System.out.println("Terima kasih!");
                    saveMenuToFile();
                    input.close();
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static int bacaInteger(Scanner input) {

        while (!input.hasNextInt()) {
            System.out.print("Masukkan angka yang valid: ");
            input.next();
        }

        int angka = input.nextInt();
        input.nextLine();

        return angka;
    }

    private static double bacaDouble(Scanner input) {

        while (!input.hasNextDouble()) {
            System.out.print("Masukkan angka yang valid: ");
            input.next();
        }

        double angka = input.nextDouble();
        input.nextLine();

        return angka;
    }

    private static void tambahMenuDefault() {

        menu.tambahMenu(new Makanan("Nasi Kebuli", 30000));
        menu.tambahMenu(new Makanan("Bakso", 15000));
        menu.tambahMenu(new Makanan("Ayam Panggang", 45000));
        menu.tambahMenu(new Makanan("Ikan Bakar", 50000));

        menu.tambahMenu(new Minuman("Es Teh", 5000));
        menu.tambahMenu(new Minuman("Teh Tarik", 10000));
        menu.tambahMenu(new Minuman("Air Mineral", 8000));
        menu.tambahMenu(new Minuman("Es Jeruk", 9000));
    }

    private static void tambahItemMenu(Scanner input) {

        System.out.println("\n=== TAMBAH MENU ===");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        System.out.print("Pilih kategori: ");

        int pilih = bacaInteger(input);

        if (pilih < 1 || pilih > 3) {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        System.out.print("Nama menu: ");
        String nama = input.nextLine();

        if (nama.trim().isEmpty()) {
            System.out.println("Nama menu tidak boleh kosong!");
            return;
        }

        if (pilih == 1 || pilih == 2) {

            System.out.print("Harga: Rp");
            double harga = bacaDouble(input);

            if (harga <= 0) {
                System.out.println("Harga harus lebih dari 0!");
                return;
            }

            if (pilih == 1) {
                menu.tambahMenu(new Makanan(nama, harga));
            } else {
                menu.tambahMenu(new Minuman(nama, harga));
            }

        } else {

            System.out.print("Persentase diskon: ");
            double diskon = bacaDouble(input);

            if (diskon <= 0 || diskon > 100) {
                System.out.println(
                    "Diskon harus lebih dari 0 dan maksimal 100!"
                );
                return;
            }

            menu.tambahMenu(new Diskon(nama, diskon));
        }

        System.out.println("Menu berhasil ditambahkan!");
    }

    private static void buatPesanan(Scanner input) {

        System.out.print("\nNama pelanggan: ");
        String pelanggan = input.nextLine();

        if (pelanggan.trim().isEmpty()) {
            System.out.println("Nama pelanggan tidak boleh kosong!");
            return;
        }

        pesanan = new Pesanan(pelanggan);

        while (true) {

            menu.tampilMenu();

            System.out.print(
                "Pilih nomor menu (0 untuk selesai): "
            );

            int pilih = bacaInteger(input);

            if (pilih == 0) {
                break;
            }

            if (pilih < 1 ||
                pilih > menu.getMenuList().size()) {

                System.out.println("Nomor menu tidak valid!");
                continue;
            }

            MenuItem item =
                menu.getMenuList().get(pilih - 1);

            if (item instanceof Diskon) {
                System.out.println(
                    "Item diskon bukan menu pesanan!"
                );
                continue;
            }

            System.out.print("Jumlah: ");

            int jumlah = bacaInteger(input);

            if (jumlah <= 0) {
                System.out.println("Jumlah tidak valid!");
                continue;
            }

            pesanan.tambahPesanan(
                new PesananItem(item, jumlah)
            );

            System.out.println(
                "Pesanan berhasil ditambahkan!"
            );
        }

        if (pesanan != null) {
            System.out.println("Pesanan selesai dibuat!");
        }
    }

    private static void saveMenuToFile() {

        try (BufferedWriter bw =
                 new BufferedWriter(
                     new FileWriter("menu.txt")
                 )) {

            for (MenuItem item : menu.getMenuList()) {

                bw.write(
                    item.getKategori() + ";" +
                    item.getNama() + ";"
                );

                if (item instanceof Diskon) {

                    Diskon diskon = (Diskon) item;

                    bw.write(
                        String.valueOf(diskon.getDiskon())
                    );

                } else {

                    bw.write(
                        String.valueOf(item.getHarga())
                    );
                }

                bw.newLine();
            }

            System.out.println("Menu berhasil disimpan!");

        } catch (IOException e) {

            System.out.println(
                "Gagal menyimpan menu: " + e.getMessage()
            );
        }
    }

    private static void loadMenuFromFile() {

        File file = new File("menu.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br =
                 new BufferedReader(
                     new FileReader(file)
                 )) {

            String baris;

            while ((baris = br.readLine()) != null) {

                String[] data = baris.split(";", -1);

                if (data.length != 3) {
                    continue;
                }

                try {

                    String kategori = data[0];
                    String nama = data[1];

                    double nilai =
                        Double.parseDouble(data[2]);

                    if (kategori.equalsIgnoreCase("Makanan")) {

                        menu.tambahMenu(
                            new Makanan(nama, nilai)
                        );

                    } else if (kategori.equalsIgnoreCase("Minuman")) {

                        menu.tambahMenu(
                            new Minuman(nama, nilai)
                        );

                    } else if (kategori.equalsIgnoreCase("Diskon")) {

                        menu.tambahMenu(
                            new Diskon(nama, nilai)
                        );
                    }

                } catch (NumberFormatException e) {

                    System.out.println(
                        "Data angka tidak valid: " + baris
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                "Gagal membaca menu: " + e.getMessage()
            );
        }
    }

    private static void saveStrukToFile() {

        if (pesanan == null) {
            return;
        }

        PrintStream outputAsli = System.out;

        ByteArrayOutputStream hasil =
            new ByteArrayOutputStream();

        try {

            System.setOut(new PrintStream(hasil));

            pesanan.tampilkanPesanan();

        } finally {

            System.setOut(outputAsli);
        }

        try (BufferedWriter bw =
                 new BufferedWriter(
                     new FileWriter("struk.txt")
                 )) {

            bw.write(hasil.toString());

            System.out.println(
                "Struk berhasil disimpan ke struk.txt!"
            );

        } catch (IOException e) {

            System.out.println(
                "Gagal menyimpan struk: " + e.getMessage()
            );
        }
    }
}
