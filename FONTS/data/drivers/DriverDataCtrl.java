package data.drivers;

class DriverDataCtrl{
    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);

        DataCtrl dataCtrl = new DataCtrl();

        boolean input = false;

        System.out.println("Opciones:");
        System.out.println("1.-Lleer archivo como bytes 2.- Escrivir archivo como bytes 3.-Salir");

        while(!input) {
            System.out.print("introducir opción: ");
            String userInput = Input.nextLine();
            switch (userInput) {
                case "1":
                    System.out.print("Dirección del archivo a leer: ");
                    String userInput = Input.nextLine();
                    byte[] res = dataCtrl.ReadFileAsBytes(userInput);
                    System.out.println(res);
                    break;
                case "2":
                    System.out.print("Texto a guardar: ");
                    String userInput = Input.nextLine();
                    byte[] texto = userInput.getBytes();
                    System.out.print("Dirección donde guardarlo: ");
                    String userInput = Input.nextLine();
                    dataCtrl.WriteBytesToFile(userInput, texto );

                    break;
                case "3":
                    input = true;
                    break;
                default:
                    System.out.println("No ha selecionado ningua de las opciones validas. \n Intentelo de nuevo.");
                    System.out.println("Opciones:");
                    System.out.println("1.-Lleer archivo como bytes 2.- Escrivir archivo como bytes 3.-Salir");
                    break;
            }
        }

    }

}