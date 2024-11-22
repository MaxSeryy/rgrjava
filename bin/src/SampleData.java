public class SampleData {
    public static ComputerStore createSampleStore() {
        // Create some data
        Product dellXPS = new Product("1", "Dell XPS 13", 999.99);
        Product macBookPro = new Product("2", "MacBook Pro", 1299.99);
        Product hpSpectre = new Product("3", "HP Spectre x360", 1199.99);
        Product lenovoThinkPad = new Product("4", "Lenovo ThinkPad X1", 1099.99);
        Product asusZenBook = new Product("9", "Asus ZenBook", 899.99);

        Category laptops = new Category("1", "Laptops", "John Doe");
        laptops.addProduct(dellXPS);
        laptops.addProduct(macBookPro);
        laptops.addProduct(hpSpectre);
        laptops.addProduct(lenovoThinkPad);
        laptops.addProduct(asusZenBook);

        Product logitechMouse = new Product("5", "Logitech MX Master 3", 99.99);
        Product razerKeyboard = new Product("6", "Razer BlackWidow", 129.99);
        Product dellMonitor = new Product("10", "Dell UltraSharp", 299.99);

        Category peripherals = new Category("2", "Peripherals", "Jane Doe");
        peripherals.addProduct(logitechMouse);
        peripherals.addProduct(razerKeyboard);
        peripherals.addProduct(dellMonitor);

        Product dellServer = new Product("7", "Dell PowerEdge T30", 499.99);
        Product hpServer = new Product("8", "HP ProLiant ML350", 599.99);
        Product ibmServer = new Product("11", "IBM System x", 699.99);

        Category servers = new Category("3", "Servers", "Mike Johnson");
        servers.addProduct(dellServer);
        servers.addProduct(hpServer);
        servers.addProduct(ibmServer);

        Department sales = new Department("1", "Sales", "Jane Smith");
        sales.addCategory(laptops);
        sales.addCategory(peripherals);

        Category networking = new Category("4", "Networking", "Alice Johnson");
        Product ciscoRouter = new Product("12", "Cisco Router", 199.99);
        Product tpLinkSwitch = new Product("13", "TP-Link Switch", 49.99);
        Product netgearModem = new Product("14", "Netgear Modem", 89.99);
        networking.addProduct(ciscoRouter);
        networking.addProduct(tpLinkSwitch);
        networking.addProduct(netgearModem);

        Department support = new Department("2", "Support", "Michael Brown");
        support.addCategory(servers);
        support.addCategory(networking);

        ComputerStore store = new ComputerStore("Tech Store", "123 Main St");
        store.addDepartment(sales);
        store.addDepartment(support);

        return store;
    }
}