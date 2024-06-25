2. Se quiere modelar la cola de un banco que atiende un solo empleado, los clientes llegan y si esperan más de 10 minutos se retiran.

Task empleado is
    Entry pedido(D: IN Text; R: OUT Text);
end empleado;

Task Type Clientes;
Cliente: array[1..C] of Clientes;

Task body empleado is
    D: Text;
    R: Text;
begin
    LOOP
        ACCEPT pedido(D, R);
            R = Resolver(D);
        end pedido;
    end LOOP;
end empleado;

Task body Clientes is
    R: Text;
begin
    empleado.pedido("Datos", R);
end Clientes;
