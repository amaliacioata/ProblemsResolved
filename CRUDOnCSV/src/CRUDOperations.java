import java.io.*;
import java.sql.SQLOutput;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


public class CRUDOperations {
    public static void main(String[] args) {
        String filePath="C:\\Users\\Vostro\\Desktop\\Cioată Cristina-Amalia\\CRUDOnCSV\\src\\products.csv";

        System.out.println("Write a line in products.csv file");
        writeProductsFile(filePath);

        System.out.println("Read products.csv file");
        readProductsFile(filePath);
        System.out.println("Update lines in products.csv file");

        System.out.println("Delete lines in products,csv file");
    }

    public static void writeProductsFile(String filePath) {
        List<Products> products=new ArrayList<>();

        Products product=new Products();
        product.setProductName("shoes");
        product.setPrice(50);
        product.setQuantity(1);

        product=new Products();
        product.setProductName("dress");
        product.setPrice(200);
        product.setQuantity(1);

        product=new Products();
        product.setProductName("belt");
        product.setPrice(15);
        product.setQuantity(1);

        FileWriter fileWriter= null;
        try{
            fileWriter = new FileWriter(filePath,true); //avoiding overwrites file

            fileWriter.append("Product Name, Price, Quantity \n");
            for(Products p: products){
                fileWriter.append(p.getProductName());
                fileWriter.append(",");
                fileWriter.append(String.valueOf(p.getPrice()));
                fileWriter.append(",");
                fileWriter.append(String.valueOf(p.getQuantity()));
                fileWriter.append("\n");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                fileWriter.flush(); //need to be sure that all your data from buffer is written
                fileWriter.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    public static void readProductsFile(String filePath) {
        BufferedReader reader = null;

        try {
            List<Products> products = new ArrayList<Products>();
            String line = "";
            reader = new BufferedReader(new FileReader("."+filePath));
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if(fields.length >0){
                    Products product=new Products();
                    product.setProductName(fields[0]);
                    product.setPrice(Integer.valueOf((fields[1])));
                    product.setQuantity(Integer.valueOf(fields[2]));
                    products.add(product);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeLineFromFile(String file, String lineToRemove) {

        try {

            File inFile = new File(file);

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(lineToRemove)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //remove the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    }
