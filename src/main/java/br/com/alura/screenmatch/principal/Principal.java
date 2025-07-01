package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner SCANNER = new Scanner(System.in);
    private final ConsumoApi CONSUMO = new ConsumoApi();
    private final ConverteDados CONVERSOR = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + System.getenv("APIKey");
    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = SCANNER.nextLine();
        String json = CONSUMO.obterDados(ENDERECO + URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8) + API_KEY);
        DadosSerie dados = CONVERSOR.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = CONSUMO.obterDados(ENDERECO + URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8) + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = CONVERSOR.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

//        for (DadosTemporada temporada : temporadas) {
//            List<DadosEpisodio> episodios = temporada.episodios();
//            for (DadosEpisodio episodio : episodios) {
//                System.out.println(episodio.titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .toList();

//        System.out.println("\nTop 10 episódios");
//        dadosEpisodios.stream()
//                .filter(e -> e.avaliacao() != null && !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("Ordenação " + e))
//                .limit(10)
//                .peek(e -> System.out.println("Limite " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .toList();

        episodios.forEach(System.out::println);

//        System.out.println("Digite um trecho do título do episódio");
//        String trechoTitulo = SCANNER.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if (episodioBuscado.isPresent()) {
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//            System.out.println();
//        } else {
//            System.out.println("Episódio não encontrado!");
//        }
//
//        System.out.print("A partir de que ano você deseja ver os episódio? ");
//        int ano = SCANNER.nextInt();
//        SCANNER.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episódio: " + e.getTitulo() +
//                                " Data lançamento: " + e.getDataLancamento().format(formatter)
//                ));

        Map<Integer, Double> avalicoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avalicoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
//        episodios.stream()
//                .filter(e -> e.getAvaliacao() == est.getMax())
//                .forEach(e -> System.out.println(e.getTitulo() + ", " + e.getTemporada() + " Temporada, Nota: " + e.getAvaliacao()));
        System.out.println("Pior episódio: " + est.getMin());
//        episodios.stream()
//                .filter(e -> e.getAvaliacao() == est.getMin())
//                .forEach(e -> System.out.println(e.getTitulo() + ", " + e.getTemporada() + " Temporada, Nota: " + e.getAvaliacao()));
        System.out.println("Quantidade: " + est.getCount());
    }
}