6. En una playa hay 5 equipos de 4 personas cada uno (en total son 20 personas donde cada una conoce previamente a que equipo
pertenece). Cuando las personas van llegando esperan con los de su equipo hasta que el mismo esté completo (hayan llegado los 4
integrantes), a partir de ese momento el equipo comienza a jugar. El juego consiste en que cada integrante del grupo junta 15 monedas
de a una en una playa (las monedas pueden ser de 1, 2 o 5 pesos) y se suman los montos de las 60 monedas conseguidas en el grupo. Al
finalizar cada persona debe conocer el grupo que más dinero junto. Nota: maximizar la concurrencia. Suponga que para simular la búsqueda
de una moneda por parte de una persona existe una función Moneda() que retorna el valor de la moneda encontrada.

Procedure playa is

    Task Type personas is
        entry Ident(id: in integer);
        entry maximo (max: in integer; idM: in integer);
    end personas;
    persona: array[1..p] of personas;

    Task Type equipos is
        entry llegue(id: in integer);
        entry sumar(suma: in integer);
        entry listos();
    end equipos;
    equipo: array[1..5] of equipos

    task comparar is
        entry totales(sumaT: in integer; ID: in integer);
    end comparar;


    task body personas is
        integer: id, idGrp, suma, max, idM;
    begin

        --ACCEPT para saber mi ID
        ACCEPT Ident(id);

        --Aviso que llegué
        equipo[idGrp].llegue(id);

        --No poner ACCEPT donde quiero bloquearme. Hacerlo con un entry call
        equipo[idGrp].listos;

        --Empieza el juego
        for i:=1 to 15 LOOP
            suma += Moneda();
        end LOOP;

        equipo[idGrp].sumar(suma);

        ACCEPT maximo(max, idM);

    end personas;

    task body comparar is
        Integer: max, idMax, cant, sumaT, ID;
    begin
        While (cant < 5) LOOP
            ACCEPT totales(sumaT, ID);
            if (sumaT > max) then
                max = sumaT;
                idMax = ID;
            end if
            cant++;
        end LOOP

        for i:=1 to 20 LOOP
            persona[i].maximo(max, idMax);
        end LOOP
    end comparar;

    task body equipos is
        Queue IDs;
        Integer: sumaT, miID;
    begin

        --ACCEPT para saber el ID de grupo
        ACCEPT IdentGrp(miID);

        --Mientras que no hayan llegado los 4, itero y pusheo los ID para dsp despertarlos
        while (cant != 4) LOOP
            ACCEPT llegue(id);
            cant++;
            IDs.push(id);
        end LOOP;

        --Una vez que llegaron, los despierto
        for i:=1 to 4 LOOP
            ACCEPT listos;
        end LOOP

        for i:=1 to 4 LOOP
            ACCEPT sumar(suma);
            sumaT += suma;
        end LOOP;

        comparar.totales(sumaT, miID);

    end equipos;


begin
    for i:=1 to 20 LOOP
        persona[i].Ident(i);
    end LOOP;

    for i:=1 to 5 LOOP
        equipo[i].IdentGrp(i);
    end LOOP;
end playa