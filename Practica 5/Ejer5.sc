5. En un sistema para acreditar carreras universitarias, hay UN Servidor que atiende pedidos de U Usuarios de a uno a la vez y de
acuerdo al orden en que se hacen los pedidos. Cada usuario trabaja en el documento a presentar, y luego lo envía al servidor;
espera la respuesta del mismo que le indica si está todo bien o hay algún error. Mientras haya algún error vuelve a trabajar con
el documento y a enviarlo al servidor. Cuando el servidor le responde que está todo bien el usuario se retira. Cuando un usuario
envía un pedido espera a lo sumo 2 minutos a que sea recibido por el servidor, pasado ese tiempo espera un minuto y vuelve a
intentarlo (usando el mismo documento).

Task servidor is
    Entry pedido(trabajo: in Text; listo: out boolean);
end servidor;

Task Type usuarios;
usuario: array[1..U] of usuarios;

task body usuarios is
    boolean listo: false;
    boolean trabajado: false;
begin
    while(not listo) LOOP
        --If que se hace para cumplir con lo ultimo de la consigna: "vuelve a intentarlo (usando el mismo documento)"
        if (not trabajado) then
            --trabajar en el documento
            trabajado = true;
        end if

        SELECT            
            servidor.pedido(trabajo, listo);
            trabajado = false;
        OR delay 2 minutes;
            delay 1 minute;
        end SELECT;
    end LOOP;
end usuarios;

task body servidor is
trabajo: Text;    
begin
    LOOP
        SELECT
            ACCEPT pedido(trabajo, listo);
                listo = corregirTrabajo;
            end ACCEPT
    end LOOP    
end servidor;