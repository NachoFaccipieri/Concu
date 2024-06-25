4. En una clínica existe un médico de guardia que recibe continuamente peticiones de atención de las E enfermeras que trabajan en
su piso y de las P personas que llegan a la clínica ser atendidos. 

Cuando una persona necesita que la atiendan espera a lo sumo 5 minutos a que el médico lo haga, si pasado ese tiempo no lo hace, 
espera 10 minutos y vuelve a requerir la atención del médico. Si no es atendida tres veces, se enoja y se retira de la clínica.

Cuando una enfermera requiere la atención del médico, si este no lo atiende inmediatamente le hace una nota y se la deja en el
consultorio para que esta resuelva su pedido en el momento que pueda (el pedido puede ser que el médico le firme algún papel).
Cuando la petición ha sido recibida por el médico o la nota ha sido dejada en el escritorio, continúa trabajando y haciendo más
peticiones.

El médico atiende los pedidos dándole prioridad a los enfermos que llegan para ser atendidos. Cuando atiende un pedido, recibe la
solicitud y la procesa durante un cierto tiempo. Cuando está libre aprovecha a procesar las notas dejadas por las enfermeras.


Task medico is
    entry atencionP;
    entry atencionE;
end medico;

Task consultorio is
    entry dejarNota;
    entry tomarNota(note: out Text);
end consultorio;

Task Type personas;
persona: array[1..P] of personas;

Task Type enfermeras;
enfermera: array[1..E] of enfermeras;

task body consultorio is    
Queue notas;
begin
    LOOP
        SELECT
            ACCEPT dejarNota(note: in text) is
                push.notas(note);
            end accept;
        OR
            ACCEPT tomarNota(note: out text) is
                if(empty(notas)) then
                    note := "VACIO";
                ELSE
                    note = pop.notas();
                end if;
            end SELECT;
        end SELECT;
    end LOOP;
end consultorio;

Task body enfermera is
Text note;
begin
    LOOP
        SELECT
            medico.atencionE;
        ELSE
            consultorio.dejarNota(note);
        end SELECT;
    end LOOP;
end enfermera;

Task body personas is
    integer cont = 0;
begin
    while(cont < 3 and not atentido) LOOP
     SELECT
            medico.atencionP;
            atentido := true;
        OR delay 5
            cont++;
            delay 10;
        end SELECT;
    end LOOP;
end persona;

task body medico is
begin
    LOOP
     SELECT
            accept atencionP(D: in Text; Res: out Text) is
                Res := atenderPersona(D);
            end atencionP;
        OR
            when(atencionP'count = 0) =>
                accept atencionE;
        ELSE
         SELECT
                consultorio.tomarNota(note);
        end SELECT;
    end LOOP;    
end medico;