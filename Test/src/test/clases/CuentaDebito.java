package test.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CuentaDebito {
    private String numCuenta;
    private Cliente cliente;
    private String nip;
    private double saldo;
    
    // Constructor que solo recibe el numero de cuenta
    public CuentaDebito(String numCuenta) {
        this.numCuenta = numCuenta;
        cargarDatosDeBaseDeDatos();
    }
    
    private void cargarDatosDeBDD() {
        String url = "???"; // URL
        String usuario = " "; // USER
        String pass = " "; 


        try (Connection conexion = DriverManager.getConnection(url, usuario, pass)) {
            String sql = "SELECT * FROM cuentas_debito WHERE num_cuenta = ?";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, this.numCuenta);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Obtener el cliente de la base de datos segun el ID almacenado en la tabla de cuentas
                    this.cliente = obtenerClientePorId(resultSet.getInt("cliente_id"), conexion);
                    this.nip = resultSet.getString("nip");
                    this.saldo = resultSet.getDouble("saldo");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Cliente obtenerClientePorId(int id, Connection conexion) throws SQLException {
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Si se encuentra el cliente, se obtienen los datos de la fila
                int clienteId = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String direccion = resultSet.getString("direccion");
                // Crear un objeto Cliente con los datos obtenidos de la base de datos
                cliente = new Cliente(clienteId, nombre, apellido, direccion);
            }
        }
        return cliente;
    }

    public double mostrarSaldo() {
        return saldo;
    }

    // Operaciones comunes
    public boolean depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            return true;
        }
        return false;
    }

    public boolean retirar(double monto, String nip) {
        if (monto > 0 && monto <= saldo && this.nip.equals(nip)) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }

    // Getters y setters para los atributos

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
        cargarDatosDeBaseDeDatos(); // Recargar datos cuando se cambia el num de cuenta
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // No se proporciona un getter para el NIP por razones de seguridad

    public void setNip(String nip) {
        this.nip = nip;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}

public class CuentaDebitoDAO {
    private Connection conexion;

    // Establece la conexion con la base de datos
    public CuentaDebitoDAO(String url, String usuario, String pass) throws SQLException {
        conexion = DriverManager.getConnection(url, usuario, pass);
    }

    // Cerrar la conexion
    public void cerrarConexion() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

    // Insertar una nueva cuenta en la base de datos
    public void insertarCuenta(CuentaDebito cuenta) throws SQLException {
        String sql = "INSERT INTO cuentas_debito (num_cuenta, cliente_id, nip, saldo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, cuenta.getNumCuenta());
            statement.setInt(2, cuenta.getCliente().getId()); // Suponiendo que el cliente tiene un ID en la base de datos
            statement.setString(3, cuenta.getNip());
            statement.setDouble(4, cuenta.getSaldo());
            statement.executeUpdate();
        }
    }

    // Obtener todas las cuentas de la base de datos
    public List<CuentaDebito> obtenerTodasLasCuentas() throws SQLException {
        List<CuentaDebito> cuentas = new ArrayList<>();
        String sql = "SELECT * FROM cuentas_debito";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String numCuenta = resultSet.getString("num_cuenta");
                // Crear cuenta solo con el num de cuenta
                CuentaDebito cuenta = new CuentaDebito(numCuenta);
                cuentas.add(cuenta);
            }
        }
        return cuentas;
    }
}