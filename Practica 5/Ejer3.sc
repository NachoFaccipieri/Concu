3. Se dispone de un sistema compuesto por 1 central y 2 procesos. Los procesos envían señales a la central. La central comienza su
ejecución tomando una señal del proceso 1, luego toma aleatoriamente señales de cualquiera de los dos indefinidamente. Al recibir
una señal de proceso 2, recibe señales del mismo proceso durante 3 minutos. El proceso 1 envía una señal que es considerada vieja
(se deshecha) si en 2 minutos no fue recibida. El proceso 2 envía una señal, si no es recibida en ese instante espera 1 minuto y
vuelve a mandarla (no se deshecha).


Task Central is
    entry Signal1();
    entry Signal2();
    entry contador();
end Task Central;

Task contador is
    entry iniciar();
end Task contador

Task proceso1;
Task proceso2;


Task Body Central is
boolean atendiendoProc2 = false;
begin
    ACCEPT Signal1();
    LOOP
        SELECT
            when(not atendiendoProc2) => 
                ACCEPT Signal1() do
                    -- recibir señal
                end Signal1;
        or
            when (contador'count = 0) =>
                ACCEPT Signal2() do
                    -- recibir señal
                    if (not atendiendoProc2) then
                        Contador.iniciar();
                        atendiendoProc2 = true;
                    end if;
                end Signal2;
        or
            ACCEPT contador() do
                atendiendoProc2 = false;
            end finContador;
    end LOOP;
end Central;

task body contador is
begin
    ACCEPT iniciar();
    dealy 3 minutos;
    Central.contador();
end contador;

Task Body proceso2 is
begin
    LOOP
        SELECT
            Central.Signal2();
        ELSE        --No se usa el "or delay 1 minuto" porque se quedaría esperando a lo sumo 1 minuto en que acepten el entry call. Con el caso del ELSE, si "Central" no acepta inmediatamente la señal, el proceso 2 se debe demorar sin hacer nada y reenviar la señal 1 minutos dsp.
            delay 1 minuto;
        end SELECT;
    end LOOP;
end proceso2;

Task Body proceso1 is
begin
    Central.Signal1();
    LOOP
        SELECT
            Central.Signal1();
        or delay 2 minutos  --Espera 2 minutos para que la señal sea recibida, caso contrario la descarta
            -- descartar señal
        end SELECT;
    end LOOP;
end Proceso1