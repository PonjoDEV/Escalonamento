import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App {

    static ArrayList<Processes> proc = new ArrayList<>();
    static int globalCounter = 0;

    public static void main(String[] args) throws Exception {
        // System.out.println("Digite a quantidade de processos: ");

        try (Scanner in = new Scanner(System.in)) {
            // int quant = in.nextInt();

            int dur = 0;
            int inter = 0;
            int priority = 0;
            String name = null;
            int interD = 0;

            /*
             * for (int i = 0; i < quant; i++) {
             * 
             * System.out.println("Qual a duração do processo " + (1 + i) + "? ");
             * // dur = in.nextInt();
             * dur = (int) Math.floor(Math.random() * 10);
             * if (dur == 0) {
             * dur = 1;
             * }
             * 
             * System.out.println("Existe alguma interrupção no processo " + (i + 1) +
             * "?\n 1-Sim\n 2-Não ");
             * // inter = in.nextInt();
             * inter = (int) Math.floor(Math.random() * 3);
             * if (inter == 1) {
             * inter = (int) Math.floor(Math.random() * dur);
             * System.out.println("Interrupção gerada aos " + inter + " segundos");
             * 
             * System.out.println("Qual a duração da interrupção ? ");
             * // dur = in.nextInt();
             * interD = (int) Math.floor(Math.random() * 10);
             * if (interD == 0) {
             * interD = 1;
             * }
             * } else {
             * System.out.println("Não existe interrupção");
             * inter = dur;
             * interD = 0;
             * }
             * 
             * System.out.println("Qual a prioridade do processo " + (i + 1) + "? ");
             * // priority = in.nextInt();
             * priority = (int) Math.floor(Math.random() * 18);
             * if (priority < -18 || priority > 18) {
             * System.out.println("Prioridade inválida, setado como menor prioridade (18)");
             * priority = 18;
             * }
             * name = ("Processo " + (i + 1));
             * Processes novo = new Processes(name, dur, inter, interD, priority, -1);
             * proc.add(novo);
             * 
             * }
             */

            // Teste

            if (true) {
                // processo 1
                name = "Processo 1";
                dur = 10;
                inter = 4;
                interD = 6;
                priority = 0;

                Processes novo = new Processes(name, dur, inter, interD, priority, -1);
                proc.add(novo);

                // p´rocesso 2
                name = "Processo 2";
                dur = 3;
                inter = 2;
                interD = 2;
                priority = -18;

                novo = new Processes(name, dur, inter, interD, priority, -1);
                proc.add(novo);

                // p´rocesso 3
                name = "Processo 3";
                dur = 8;
                inter = 5;
                interD = 5;
                priority = 10;

                // novo = new Processes(name, dur, inter, interD, priority, -1);
                // proc.add(novo);
            }

            for (int i = 0; i < proc.size(); i++) {
                System.out.println("\n" + proc.get(i).getNames());
                System.out.println("Duração " + proc.get(i).getDur());
                System.out.println("Momento da interrupção " + proc.get(i).getInter());
                System.out.println("Duração da interrupção " + proc.get(i).getInterDur());
                System.out.println("Prioridade " + proc.get(i).getPriority());
            }
            FCFS();
            SJF();
            Dulling();
            RR();
            SRT();
        }
    }

    private static void SRT() {

    }

    private static void RR() throws CloneNotSupportedException {
        System.out.println("TEMPO GLOBAL ATUAL " + globalCounter
                + "\n\n\nMétodo RR\nDefina quantos segundos a duração do quantum: ");
        Scanner in = new Scanner(System.in);
        int quantum = in.nextInt();
        System.out.println("Quantum definido como " + quantum + " segundos\n");
        ArrayList<Processes> copy = new ArrayList<Processes>();
        for (Processes p : proc) {
            copy.add((Processes) p.clone());
        }
        int aux = 0;
        // testing to see if tehre are no more processes to run
        while (!copy.isEmpty()) {
            aux = 0;
            for (int i = 0; i < copy.size(); i++) {
                if (copy.get(i).getLastRun() != -1
                        && globalCounter < (copy.get(i).getLastRun() + copy.get(i).getInterDur())) {
                    aux++;
                    if (aux == copy.size() && aux != 0) {
                        halt();
                    }
                } else {
                    execute(copy.get(i), quantum);
                    // talvez trocar de lugar para o final do método(?)
                    if (copy.get(i).getDur() == 0) {
                        copy.remove(i);
                    }
                }
            }
        }
        System.out.println("TEMPO GLOBAL ATUAL " + globalCounter);

        globalCounter = 0;
    }

    private static void Dulling() throws CloneNotSupportedException {

        System.out.println("TEMPO GLOBAL ATUAL " + globalCounter + "\n\n\nMétodo Dulling ");

        ArrayList<Processes> copy = new ArrayList<Processes>();
        for (Processes p : proc) {
            copy.add((Processes) p.clone());
        }
        int aux = 0;
        // sorting the array by its priority
        copy.sort(Comparator.comparing(Processes::getPriority));
        // testing to see if tehre are no more processes to run
        while (!copy.isEmpty()) {
            aux = 0;
            for (int i = 0; i < copy.size(); i++) {
                if (copy.get(i).getLastRun() != -1
                        && globalCounter < (copy.get(i).getLastRun() + copy.get(i).getInterDur())) {
                    aux++;
                    if (aux == copy.size() && aux != 0) {
                        halt();
                    }
                } else {
                    execute(copy.get(i), 0);
                    if (copy.get(i).getDur() == 0) {
                        copy.remove(i);
                    }
                }
            }

        }

        System.out.println("TEMPO GLOBAL ATUAL " + globalCounter);

        globalCounter = 0;
    }

    private static void FCFS() throws CloneNotSupportedException {
        System.out.println("TEMPO GLOBAL ATUAL " + globalCounter + "\n\n\nMétodo FCFS ");
        ArrayList<Processes> copy = new ArrayList<Processes>();
        for (Processes p : proc) {
            copy.add((Processes) p.clone());
        }

        int aux = 0;
        // testing to see if tehre are no more processes to run
        while (!copy.isEmpty()) {
            aux = 0;
            for (int i = 0; i < copy.size(); i++) {
                if (copy.get(i).getLastRun() != -1
                        && globalCounter < (copy.get(i).getLastRun() + copy.get(i).getInterDur())) {
                    aux++;
                    if (aux == copy.size() && aux != 0) {
                        halt();
                    }
                } else {
                    execute(copy.get(i), 0);
                    if (copy.get(i).getDur() == 0) {
                        copy.remove(i);
                    }
                }
            }

        }
        System.out.println("TEMPO GLOBAL ATUAL " + globalCounter);

        globalCounter = 0;
    }

    private static void SJF() throws CloneNotSupportedException {
        System.out.println("TEMPO GLOBAL ATUAL " + globalCounter + "\n\n\nMétodo SJF ");
        ArrayList<Processes> copy = new ArrayList<Processes>();

        for (Processes p : proc) {
            copy.add((Processes) p.clone());
        }
        int aux = 0;
        // testing to see if tehre are no more processes to run
        while (!copy.isEmpty()) {
            aux = 0;
            // sorting the array by its duration
            copy.sort(Comparator.comparing(Processes::getDur));
            for (int i = 0; i < copy.size(); i++) {
                if (copy.get(i).getLastRun() != -1
                        && globalCounter < (copy.get(i).getLastRun() + copy.get(i).getInterDur())) {
                    aux++;
                    if (aux == copy.size() && aux != 0) {
                        halt();
                    }
                } else {
                    execute(copy.get(i), 0);
                    if (copy.get(i).getDur() == 0) {
                        copy.remove(i);
                    }
                }
            }

        }

        System.out.println("TEMPO GLOBAL ATUAL " + globalCounter);

        globalCounter = 0;
    }

    // tests if there is a interuption then calling the method to write
    private static void execute(Processes currentProcess, int quantum) {
        // it first prints the name of the process which is about to begin
        System.out.println("\n------ Executando " + currentProcess.getNames());
        if (currentProcess.getDur() != 0) {
            // we declared that if the interuption happens at the end of the proccess, means
            // there are no more INTERRUPTIONS
            if (currentProcess.getInter() == currentProcess.getDur()) {
                // testing if there is no quantum, (if there is not a defined well treat it as
                // quantum being equalto 0), or if its greater
                // than the process duration
                if (quantum >= currentProcess.getDur() || quantum <= 0) {
                    currentProcess.setLastRun(escrever(currentProcess.getDur()));
                    currentProcess.setDur(0);
                    currentProcess.setInter(0);
                } // if there is a defined quantum, well execute the process until it is reached.
                else {
                    currentProcess.setLastRun(escrever(quantum));
                    currentProcess.setDur((currentProcess.getDur() - quantum));
                    currentProcess.setInter(currentProcess.getDur());
                    currentProcess.setInterDur(0);
                }

            } // if thats not the case, we'll execute the process until it gets to the
              // interruption point
            else {
                // testing if there is a quantum, (well treat it as not existing if =<0)
                if (quantum <= 0 || quantum >= currentProcess.getInter()) {
                    currentProcess.setLastRun(escrever(currentProcess.getInter()));
                    currentProcess.setDur((currentProcess.getDur() - currentProcess.getInter()));
                    currentProcess.setInter(currentProcess.getDur());
                } else {
                    // currentProcess.setLastRun(escrever(quantum));
                    escrever(quantum);
                    currentProcess.setDur(currentProcess.getDur() - quantum);
                    currentProcess.setInter(currentProcess.getInter() - quantum);

                    if (currentProcess.getInter() <= 0) {
                        currentProcess.setInterDur(0);
                        currentProcess.setInter(currentProcess.getDur());
                    }
                }
            }
        }
    }

    // method that will type the seconds elapsed in each process, while returning
    // the current globaltimer
    private static int escrever(int aux) {
        System.out.println("Excutando por: ");
        for (int i = 0; i < aux; i++) {
            // updating the globaltimer each time it "runs" a process for 1 sec
            globalCounter++;
            System.out.print((i + 1) + " segundos... ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("");
        return globalCounter;
    }

    // if there are no more available processes to execute, it will halt all of them
    // and update the global timer
    public static void halt() {
        System.out.println("\nNão há processo disponível para execução, gerando 1 segundo de interrupção...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // updating the global timer
        globalCounter++;
    }
}
