8. Una empresa de limpieza se encarga de recolectar residuos en una ciudad por medio de 3 camiones. Hay P personas que hacen
continuos reclamos hasta que uno de los camiones pase por su casa. Cada persona hace un reclamo, espera a lo sumo 15 minutos a que
llegue un camión y si no vuelve a hacer el reclamo y a esperar a lo sumo 15 minutos a que llegue un camión y así sucesivamente hasta
que el camión llegue y recolecte los residuos; en ese momento deja de hacer reclamos y se va. Cuando un camión está libre la empresa
lo envía a la casa de la persona que más reclamos ha hecho sin ser atendido. Nota: maximizar la concurrencia.


Procedure empLimpieza is

    Task Type personas is
        entry Ident(i: integer);
    end personas;
    persona: array[1..P] of personas;

    Task Type camiones;
    camion: array[1..3] of camiones;

    Task empresa is
        entry reclamo(idP: in integer);
        entry camionLibre(idP: out integer);
    end empresa;

    Task body camiones is
    end camiones;

    Task body camiones is
    begin
        loop
            empresa.camionLibre(idP);
            persona[idP].pasaCamion();
        end loop;
    end camiones;

    Task body empresa is
        cantReclamos: array[1..p] of integer;
        max: integer;
    begin
        loop
            SELECT
                ACCEPT reclamo(idP: in integer) do
                    cantReclamos[idP] := cantReclamos[idP] + 1;
                end reclamo;
            OR
                ACCEPT camionLibre(idP) do
                    for i:=1 to P loop
                        if(cantReclamos[i] > cantReclamos[max]) then
                            max := i;
                        end if;
                    end loop;
                    if(cantReclamos[max] > 0) then
                        cantReclamos[max] := 0;
                    else
                        max := 0;
                    end if;
                end camionLibre;
            end SELECT;                
        end loop;
    end empresa;

    Task body personas is
        listo: boolean;
        miId: integer;
    begin
        accept Ident(miId);

        while(not listo) loop
            empresa.reclamo(miId);
            SELECT
                ACCEPT pasaCamion() is
                    listo := true;
                end pasaCamion;
            OR DELAY 15 minutos;
            end select;
        end loop;
    end personas;

begin
    for i:=1 to P loop
        persona[i].Ident(i);
    end loop;
end empLimpieza;
