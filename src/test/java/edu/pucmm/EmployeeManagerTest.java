package edu.pucmm;


import edu.pucmm.exception.DuplicateEmployeeException;
import edu.pucmm.exception.EmployeeNotFoundException;
import edu.pucmm.exception.InvalidSalaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author me@fredpena.dev
 * @created 02/06/2024  - 00:47
 */

public class EmployeeManagerTest {

    private EmployeeManager employeeManager;
    private Position juniorDeveloper;
    private Position seniorDeveloper;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    public void setUp() {
        employeeManager = new EmployeeManager();
        juniorDeveloper = new Position("1", "Junior Developer", 30000, 50000);
        seniorDeveloper = new Position("2", "Senior Developer", 60000, 90000);
        employee1 = new Employee("1", "John Doe", juniorDeveloper, 40000);
        employee2 = new Employee("2", "Jane Smith", seniorDeveloper, 70000);
        employeeManager.addEmployee(employee1);
    }

    @Test
    public void testAddEmployee() {
        // TODO: Agregar employee2 al employeeManager y verificar que se agregó correctamente.
        employeeManager.addEmployee(employee2);
        System.out.println("Employee added: " + employee2);
        // - Verificar que el número total de empleados ahora es 2.
        assertEquals(2, employeeManager.getEmployees().size(), "Employee es 2 luego de agregar a employee2");
        // - Verificar que employee2 está en la lista de empleados.
        assertTrue(employeeManager.getEmployees().contains(employee2), "Employee2 esta en la lista de empleados");
    }

    @Test
    public void testRemoveEmployee() {
        // TODO: Eliminar employee1 del employeeManager y verificar que se eliminó correctamente.
        employeeManager.removeEmployee(employee1);
        System.out.println("Employee removed: " + employee1);
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Verificar que el número total de empleados ahora es 1.
        assertEquals(1, employeeManager.getEmployees().size(), "Employee es 1 luego de eliminar a employee1");
        // - Verificar que employee1 ya no está en la lista de empleados.
        assertFalse(employeeManager.getEmployees().contains(employee1), "Employee1 no esta en la lista de empleados");
    }

    @Test
    public void testCalculateTotalSalary() {
        // TODO: Agregar employee2 al employeeManager y verificar el cálculo del salario total.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Verificar que el salario total es la suma de los salarios de employee1 y employee2.
        double expectedTotalSalary = employee1.getSalary() + employee2.getSalary();
        System.out.println("Expected total salary: " + expectedTotalSalary);
        assertEquals(expectedTotalSalary, employeeManager.calculateTotalSalary(), "El salario total es correcto");
    }

    @Test
    public void testUpdateEmployeeSalaryValid() {
        // TODO: Actualizar el salario de employee1 a una cantidad válida y verificar la actualización.
        // - Actualizar el salario de employee1 a 45000.
        employeeManager.updateEmployeeSalary(employee1, 45000);
        // - Verificar que el salario de employee1 ahora es 45000.
        assertEquals(45000, employee1.getSalary(), "El salario del employee1 es: 45000");
    }

    @Test
    public void testUpdateEmployeeSalaryInvalid() {
        // TODO: Intentar actualizar el salario de employee1 a una cantidad inválida y verificar la excepción.
        // - Intentar actualizar el salario de employee1 a 60000 (que está fuera del rango para Junior Developer).
        employeeManager.updateEmployeeSalary(employee1, 60000);
        // - Verificar que se lanza una InvalidSalaryException.
        try {
            employeeManager.updateEmployeeSalary(employee1, 60000);
            fail("InvalidSalaryException esperado");
        } catch (InvalidSalaryException e) {
            assertEquals("Salario fuera de rango", e.getMessage());
        }
    }

    @Test
    public void testUpdateEmployeeSalaryEmployeeNotFound() {
        // TODO: Intentar actualizar el salario de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar el salario de employee2 a 70000.
        // - Verificar que se lanza una EmployeeNotFoundException.
        try {
            employeeManager.updateEmployeeSalary(employee2, 70000);
            fail("EmployeeNotFoundException esperado");
        } catch (EmployeeNotFoundException e) {
            assertEquals("Empleado no encontrado", e.getMessage());
        }
    }

    @Test
    public void testUpdateEmployeePositionValid() {
        // TODO: Actualizar la posición de employee2 a una posición válida y verificar la actualización.
        // - Agregar employee2 al employeeManager.
        employeeManager.addEmployee(employee2);
        // - Actualizar la posición de employee2 a seniorDeveloper.
        employeeManager.updateEmployeePosition(employee2, seniorDeveloper);
        // - Verificar que la posición de employee2 ahora es seniorDeveloper.
        assertEquals(seniorDeveloper, employee2.getPosition(), "La posición de employee2 es: seniorDeveloper");
        assertTrue(true);
    }

    @Test
    public void testUpdateEmployeePositionInvalidDueToSalary() {
        // TODO: Intentar actualizar la posición de employee1 a seniorDeveloper y verificar la excepción.
        // - Intentar actualizar la posición de employee1 a seniorDeveloper.
        // - Verificar que se lanza una InvalidSalaryException porque el salario de employee1 no está dentro del rango para Senior Developer.
        try {
            employeeManager.updateEmployeePosition(employee1, seniorDeveloper);
            fail("InvalidSalaryException esperado");
        } catch (InvalidSalaryException e) {
            assertEquals("Salario no está dentro del rango para la nueva posición", e.getMessage());
        }
    }

    @Test
    public void testUpdateEmployeePositionEmployeeNotFound() {
        // TODO: Intentar actualizar la posición de employee2 (no agregado al manager) y verificar la excepción.
        // - Intentar actualizar la posición de employee2 a juniorDeveloper.
        // - Verificar que se lanza una EmployeeNotFoundException.
        try{
            employeeManager.updateEmployeePosition(employee2, juniorDeveloper);
            fail("EmployeeNotFoundException esperado");
    } catch (EmployeeNotFoundException e) {
        assertEquals("Empleado no encontrado", e.getMessage());
    }
    }

    @Test
    public void testIsSalaryValidForPosition() {
        // TODO: Verificar la lógica de validación de salario para diferentes posiciones.
         // - El salario es negativo.
        assertFalse(employeeManager.isSalaryValidForPosition(juniorDeveloper, -1000), "El salario negativo no es válido para juniorDeveloper");
        // - El Position es null.
        assertFalse(employeeManager.isSalaryValidForPosition(null, 40000), "El salario no es válido para una posición nula");
        // - Verificar que un salario de 40000 es válido para juniorDeveloper.
        assertTrue(employeeManager.isSalaryValidForPosition(juniorDeveloper, 40000), "El salario de 40000 es válido para juniorDeveloper");
        // - Verificar que un salario de 60000 no es válido para juniorDeveloper.
        assertFalse(employeeManager.isSalaryValidForPosition(juniorDeveloper, 60000), "El salario de 60000 no es válido para juniorDeveloper");
        // - El salario es negativo.
        assertFalse(employeeManager.isSalaryValidForPosition(seniorDeveloper, -1000), "El salario negativo no es válido para seniorDeveloper");
        // - Verificar que un salario de 70000 es válido para seniorDeveloper.
        assertTrue(employeeManager.isSalaryValidForPosition(seniorDeveloper, 70000), "El salario de 70000 es válido para seniorDeveloper");
        // - Verificar que un salario de 50000 no es válido para seniorDeveloper.
        assertFalse(employeeManager.isSalaryValidForPosition(seniorDeveloper, 50000), "El salario de 50000 no es válido para seniorDeveloper");
    }

    @Test
    public void testAddEmployeeWithInvalidSalary() {
        // TODO: Intentar agregar empleados con salarios inválidos y verificar las excepciones.
        // - Crear un empleado con un salario de 60000 para juniorDeveloper.
        Employee invalidJuniorEmployee = new Employee("3", "Invalid Junior", juniorDeveloper, 60000);
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        try{
            employeeManager.addEmployee(invalidJuniorEmployee);
            fail("InvalidSalaryException esperado");
        }catch (InvalidSalaryException e) {
            assertEquals("Employee no aceptado, salario erroneo", e.getMessage());
        }
        // - Crear otro empleado con un salario de 40000 para seniorDeveloper.
        Employee invalidSeniorEmployee = new Employee("4", "Invalid Senior", seniorDeveloper, 40000);
        // - Verificar que se lanza una InvalidSalaryException al agregar este empleado.
        try {
            employeeManager.addEmployee(invalidSeniorEmployee);
            fail("InvalidSalaryException esperado");
        } catch (InvalidSalaryException e) {
            assertEquals("Employee no aceptado, salario erroneo", e.getMessage());
        }

    }

    @Test
    public void testRemoveExistentEmployee() {
        // TODO: Eliminar un empleado existente y verificar que no se lanza una excepción.
        // - Eliminar employee1 del employeeManager.
        assertFalse(employeeManager.getEmployees().contains(employee1), "Employee1 no debería estar en la lista de empleados después de ser eliminado");
        // - Verificar que no se lanza ninguna excepción.
        assertDoesNotThrow(() -> employeeManager.removeEmployee(employee1), "No se esperaba una excepción al eliminar employee1");
    }

    @Test
    public void testRemoveNonExistentEmployee() {
        // TODO: Intentar eliminar un empleado no existente y verificar la excepción.
        // - Intentar eliminar employee2 (no agregado al manager).
        // - Verificar que se lanza una EmployeeNotFoundException.
        try {
            employeeManager.removeEmployee(employee2);
            fail("EmployeeNotFoundException esperado");
        } catch (EmployeeNotFoundException e) {
            assertEquals("Empleado no encontrado", e.getMessage());
        }
    }

    @Test
    public void testAddDuplicateEmployee() {
        // TODO: Intentar agregar un empleado duplicado y verificar la excepción.
        // - Intentar agregar employee1 nuevamente al employeeManager.
        // - Verificar que se lanza una DuplicateEmployeeException.
        try {
            employeeManager.addEmployee(employee1);
            fail("DuplicateEmployeeException esperado");
        } catch (DuplicateEmployeeException e) {
            assertEquals("Empleado duplicado", e.getMessage());
        }

    }
}
