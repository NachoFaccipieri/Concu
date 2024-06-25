7. Hay un sistema de reconocimiento de huellas dactilares de la policía que tiene 8 Servidores para realizar el reconocimiento, cada
uno de ellos trabajando con una Base de Datos propia; a su vez hay un Especialista que utiliza indefinidamente. El sistema funciona
de la siguiente manera: el Especialista toma una imagen de una huella (TEST) y se la envía a los servidores para que cada uno de
ellos le devuelva el código y el valor de similitud de la huella que más se asemeja a TEST en su BD; al final del procesamiento, el
especialista debe conocer el código de la huella con mayor valor de similitud entre las devueltas por los 8 servidores. Cuando ha
terminado de procesar una huella comienza nuevamente todo el ciclo. Nota: suponga que existe una función Buscar(test, código, valor)
que utiliza cada Servidor donde recibe como parámetro de entrada la huella test, y devuelve como parámetros de salida el código y el
valor de similitud de la huella más parecida a test en la BD correspondiente. Maximizar la concurrencia y no generar demora innecesaria.



Procedure policía is

    Task Type servidores is
        entry huella(test: in Text);
    end servidores;
    servidor: array[1..8] of servidores;

    Task especialista is
        entry resultado();
    end especialista;

    task body servidores is
        Text: testAux;
        Integer: codigo, valor;
    begin
        accept huella(test) do
            testAux = test;
        end accept;
        Buscar(testAux, codigo, valor);
        especialista.resultado(codigo, valor);
    end servidores;


    task body especialista is
        Text: test, mayorSim;
        Integer: valorSim, valor;
        Queue resultados;
    begin
        loop
            test = tomarImagen();
            for i:=1 to 8 loop
                servidor[i].huella(test);
            end loop;

            for i:=1 to 8 loop
                accept resultado(test, valor) do
                    resultados.push(test, valor);
                end accept;
            end loop;

            resultado.pop(mayorSim, valorSim);
            for i:=1 to 7 loop
                resultados.pop(test, valor);
                if (test < mayorSim) then
                    mayorSim = test;
                    valorSim = valor;
            end loop;
        end loop;
    end especialista;

