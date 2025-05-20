

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Clase principal que representa una aplicación de consola para gestionar socios de un club.
 * Permite añadir, eliminar, modificar y listar socios, así como persistir la información en un archivo binario.
 *
 * @author Antonio
 * @version 1.0
 * @since 2025-05-20
 */
public class Ejercicio01 {

    /**
     * Clase interna que representa un socio del club.
     * Implementa {@link Comparable} para ordenación por DNI y {@link Serializable} para persistencia.
     */
    public static class Socio implements Comparable<Socio>, Serializable {

        /** DNI del socio (identificador único). */
        String dni;

        /** Nombre completo del socio. */
        String nombre;

        /** Fecha de alta en el club. */
        LocalDate fechaAlta;

        /**
         * Constructor completo que crea un socio con todos los datos.
         *
         * @param dni      Documento Nacional de Identidad del socio.
         * @param nombre   Nombre completo.
         * @param alta     Fecha de alta en formato dd/MM/yyyy.
         */
        public Socio(String dni, String nombre, String alta) {
            this.dni = dni;
            this.nombre = nombre;
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.fechaAlta = LocalDate.parse(alta, f);
        }

        /**
         * Constructor auxiliar para búsquedas y comparación (usa sólo el DNI).
         *
         * @param dni DNI del socio.
         */
        public Socio(String dni) {
            this.dni = dni;
        }

        /**
         * Calcula la antigüedad del socio en años desde su fecha de alta hasta hoy.
         *
         * @return Antigüedad en años.
         */
        int antiguedad() {
            return (int) fechaAlta.until(LocalDate.now(), ChronoUnit.YEARS);
        }

        /**
         * Compara dos socios alfabéticamente por DNI.
         *
         * @param o Socio a comparar.
         * @return Valor negativo, cero o positivo según el orden del DNI.
         */
        @Override
        public int compareTo(Socio o) {
            return dni.compareTo(o.dni);
        }

        /**
         * Verifica la igualdad entre dos socios basada en el DNI.
         *
         * @param o Objeto a comparar.
         * @return true si tienen el mismo DNI.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Socio socio = (Socio) o;
            return Objects.equals(dni, socio.dni);
        }

        /**
         * Calcula el hash code basado en el DNI.
         *
         * @return Código hash del socio.
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(dni);
        }

        /**
         * Devuelve una representación en texto del socio.
         *
         * @return Cadena con DNI, nombre y antigüedad.
         */
        @Override
        public String toString() {
            return "Socio{" +
                    "dni='" + dni + '\'' +
                    ", nombre='" + nombre + '\'' +
                    ", antiguedad=" + antiguedad() +
                    '}';
        }
    }

    /**
     * Método principal que inicia el programa y gestiona la interacción con el usuario.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Socio> socios = new ArrayList<>();
        int opcion;
        boolean seguir = true;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        File archivo = new File("archivoSocios.bin");

        // Comprobación de existencia de archivo
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo.");
            }
        }

        // Lectura de socios desde archivo
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                socios.add((Socio) ois.readObject());
            }
        } catch (EOFException e) {
            // Fin de archivo alcanzado
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada.");
        }

        // Menú de interacción
        while (seguir) {
            System.out.println("¿Qué deseas hacer?:");
            System.out.println("1. Dar de alta a un socio.");
            System.out.println("2. Dar de baja a un socio.");
            System.out.println("3. Modificar datos de un socio.");
            System.out.println("4. Listar socios por nombre.");
            System.out.println("5. Listar socios por antigüedad en el club.");
            System.out.println("6. Salir.");
            System.out.print("Elección: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpieza de buffer
            System.out.println("--------------------------------------------------");

            switch (opcion) {
                case 1: // Alta de socio
                    int dia, mes, ano;
                    String diaf, mesf;
                    System.out.println("Dime el dni:");
                    String dni = sc.nextLine();
                    System.out.println("Dime el nombre:");
                    String nombre = sc.nextLine();

                    // Fecha: validación de año
                    while (true) {
                        System.out.print("Escribe el año de alta: ");
                        ano = sc.nextInt();
                        sc.nextLine();
                        if (ano > LocalDate.now().getYear() || ano < 1950) {
                            System.out.println("Año fuera de rango válido.");
                        } else {
                            break;
                        }
                    }

                    // Validación de mes
                    while (true) {
                        System.out.print("Escribe el mes de alta: ");
                        mes = sc.nextInt();
                        sc.nextLine();
                        if (mes < 1 || mes > 12) {
                            System.out.println("Mes incorrecto.");
                        } else {
                            break;
                        }
                    }

                    // Validación de día
                    while (true) {
                        System.out.print("Escribe el día de alta: ");
                        dia = sc.nextInt();
                        sc.nextLine();
                        if (dia < 1 || dia > 31 || (dia > 30 && List.of(4, 6, 9, 11).contains(mes)) || (dia > 28 && mes == 2)) {
                            System.out.println("Día no válido para ese mes.");
                        } else {
                            break;
                        }
                    }

                    // Formateo de fecha
                    diaf = (dia < 10 ? "0" + dia : String.valueOf(dia));
                    mesf = (mes < 10 ? "0" + mes : String.valueOf(mes));
                    String fechaAlta = diaf + "/" + mesf + "/" + ano;

                    socios.add(new Socio(dni, nombre, fechaAlta.trim()));
                    System.out.println("Socio creado.");
                    System.out.println("--------------------------------------------------");
                    break;

                case 2: // Baja de socio
                    System.out.println("Dime el DNI del socio a eliminar:");
                    String dniBorrar = sc.nextLine();
                    Socio socioABorrar = new Socio(dniBorrar);
                    if (socios.remove(socioABorrar)) {
                        System.out.println("Socio eliminado correctamente.");
                    } else {
                        System.out.println("No se encontró ningún socio con ese DNI.");
                    }
                    break;

                case 3: // Modificación de socio
                    System.out.print("Dime el DNI del socio a modificar: ");
                    String dniModificar = sc.nextLine();
                    Socio socioTemp = new Socio(dniModificar);
                    int index = socios.indexOf(socioTemp);

                    if (index == -1) {
                        System.out.println("No se encontró ningún socio con ese DNI.");
                    } else {
                        Socio socioAModificar = socios.get(index);
                        System.out.println("Socio encontrado: " + socioAModificar);

                        System.out.println("¿Qué deseas modificar?");
                        System.out.println("1. Nombre");
                        System.out.println("2. Fecha de alta");
                        System.out.println("3. Ambos");
                        int eleccion = sc.nextInt();
                        sc.nextLine();

                        if (eleccion == 1 || eleccion == 3) {
                            System.out.print("Nuevo nombre: ");
                            socioAModificar.nombre = sc.nextLine();
                        }

                        if (eleccion == 2 || eleccion == 3) {
                            System.out.print("Nueva fecha de alta (dd/MM/yyyy): ");
                            String nuevaFecha = sc.nextLine();
                            try {
                                socioAModificar.fechaAlta = LocalDate.parse(nuevaFecha, formato);
                            } catch (Exception e) {
                                System.out.println("Fecha no válida. No se modificó.");
                            }
                        }

                        System.out.println("Datos actualizados: " + socioAModificar);
                    }
                    System.out.println("--------------------------------------------------");
                    break;

                case 4: // Listar socios por nombre
                    socios.sort(Comparator.comparing(x -> x.nombre));
                    socios.forEach(System.out::println);
                    System.out.println("--------------------------------------------------");
                    break;

                case 5: // Listar socios por antigüedad
                    socios.sort((x, y) -> Integer.compare(y.antiguedad(), x.antiguedad()));
                    socios.forEach(System.out::println);
                    System.out.println("--------------------------------------------------");
                    break;

                case 6: // Salir y guardar archivo
                    System.out.println("Hasta la próxima.");
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
                        for (Socio socio : socios) {
                            oos.writeObject(socio);
                        }
                    } catch (IOException e) {
                        System.out.println("Error al guardar el archivo.");
                    }
                    seguir = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
}