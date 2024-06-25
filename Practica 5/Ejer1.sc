--1. Se requiere modelar un puente de un solo sentido, el puente solo soporta el peso de 5 unidades de peso. Cada auto pesa 1 unidad,
--cada camioneta pesa 2 unidades y cada camión 3 unidades. Suponga que hay una cantidad innumerable de vehículos (A autos, B
--camionetas y C camiones). 
--a. Realice la solución suponiendo que todos los vehículos tienen la misma prioridad.
--b. Modifique la solución para que tengan mayor prioridad los camiones que el resto de los vehículos.

Task puente is
    Entry solicitud(Peso: IN Integer);
    Entry entrada(Paso: OUT boolean);
    Entry salida(Peso: IN Integer);
end puente;

Task Type vehículos;
Vehículo := array[1..V] of vehiculos;

Task body vehiculos is
    char tipo;
    integer peso;
    boolean paso;
begin
    puente.solicitud(peso);
    puente.entrada(paso);
    while (paso <> true) LOOP
        puente.solicitud(peso);
        puente.entrada(paso);
    end LOOP;
end vehiculos;    

Task body puente is
    pAct: Integer;
    boolean paso;
begin
    LOOP
        SELECT
            ACCEPT salida(Peso: IN integer) do
                pAct := pAct - Peso;
            end salida;
        OR
            ACCEPT solicitud(Peso: IN integer) do
                if (pAct + Peso) <= 5 then
                    ACCEPT entrada(paso);
                        paso = true;
                    end entrada;
                else
                    ACCEPT entrada(paso);
                        paso = false;
                    end entrada;
                end if;
            end solicitud;
        END SELECT;
    end LOOP;    
end puente;


----------Inciso B----------

Task puente is
    Entry solicitudPr(Peso: IN Integer);
    Entry solicitud(Peso: IN Integer);
    Entry entrada(Paso: OUT boolean);
    Entry salida(Peso: IN Integer);
end puente;

Task Type vehículos;
Vehículo := array[1..V] of vehiculos;

Task body vehiculos is
    char tipo;
    integer peso;
begin
    if (tipo == 'C') then
        puente.solicitudPr(3);
    else
        puente.solicitudPr(peso);
    end if;
end vehiculos;

Task body puente is
    pAct: Integer;
    boolean paso;
begin
    LOOP
        SELECT
            ACCEPT salida(Peso: IN integer) do
                pAct := pAct - Peso;
            end salida;
        OR
            ACCEPT solicitudPr(Peso: IN integer) do
                if (pAct + Peso) <= 5 then
                    ACCEPT entrada(paso);
                        paso = true;
                    end entrada;
                else
                    ACCEPT entrada(paso);
                        paso = false;
                    end entrada;
                end if;
            end solicitudPr;
        OR
            When (solicitudPr'count = 0) =>
                ACCEPT solicitud(Peso: IN integer) do
                    if (pAct + Peso) <= 5 then
                        ACCEPT entrada(paso);
                            paso = true;
                        end entrada;
                    else
                        ACCEPT entrada(paso);
                            paso = false;
                        end entrada;
                    end if;
                end solicitud;
        END SELECT;
    end LOOP;    
end puente;