import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Clima {
    
    String temperatura(String ciudad){
        List<Map<String, Object>> condicionesClimaticas = this.climaFetch(ciudad);
        return farenheitToCelsius(condicionesClimaticas.get(0).get("Temperature").get("Value"));
    }

    private List<Map<String, Object>> climaFetch(String ciudad){
        return new AccuWeatherAPI().getWeather(ciudad); 
    }

    private int fahrenheitToCelsius(int fahrenheit){
        return (fahrenheit - 32) * (5/9); 

    }

    
}

class AccuWeatherAPI{
    public final List<Map<String, Object>> getWeather(String ciudad) {
		return Arrays.asList(new HashMap<String, Object>(){{
			put("DateTime", "2019-05-03T01:00:00-03:00");
			put("EpochDateTime", 1556856000);
			put("WeatherIcon", 33);
			put("IconPhrase", "Clear");
			put("IsDaylight", false);
			put("PrecipitationProbability", 0);
			put("MobileLink", "http://m.accuweather.com/en/ar/villa-vil/7984/");
			put("Link", "http://www.accuweather.com/en/ar/villa-vil/7984");
			put("Temperature", new HashMap<String, Object>(){{
				put("Value", 57);
				put("Unit", "F");
				put("UnitType", 18);
			}});
		}});
	}
}

public interface GeneradorSugerencias {
    public List<Sugerencia> generarSugerenciasDesde(List<Prenda> prendasAptas){
        return null; //magia
    }    
}


public class Prenda{

    public Boolean validaParaTemperatura(){
        return True; // calculo a partir de material, etc..
    }
}

public class Sugerencia{
    Prenda superior;
    Prenda inferior;
    Prenda calzado;
}

public class Usuario implements GeneradorSugerencias{
    private Locacion residencia;
    private List<Prenda> prendas;

    public List<Sugerencia> sugerenciasDia(){
        final List<Prenda> prendasAptas = 
        prendas
        .stream()
        .filter((Prenda prenda) -> prenda.validaParaTemperatura())
        .collect(Collectors.toList());
        this.generarSugerenciasDesde(prendasAptas);
    }
}


public class Locacion{
    String ciudad;
    String pais;
    String direccion;
}


