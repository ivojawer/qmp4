import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Clima {
    
    public int temperatura(String ciudad){
        List<Map<String, Object>> climaActual = this.climaFetch(ciudad);
        return farenheitToCelsius(climaActual.get(0).get("Temperature").get("Value"));
    }

    private List<Map<String, Object>> climaFetch(String ciudad){
        return new AccuWeatherAPI().getWeather(ciudad); 
    }

    private int fahrenheitToCelsius(int fahrenheit){
        return (fahrenheit - 32) * (5/9); 

    }    
}

class ClimaMock extends Clima{
    @Override
    private List<Map<String, Object>> climaFetch(String ciudad){
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

public class Usuario implements GeneradorSugerencias{
    private Locacion residencia;
    private List<Prenda> prendas;

    public List<Sugerencia> sugerenciasDia(){
        this.sugerenciasDiaPorTemperatura(new Clima());
    }

    public List<Sugerencia> sugerenciasDiaPorTemperatura(Clima climaGetter){
        final List<Prenda> prendasAptas = 
        prendas
        .stream()
        .filter((Prenda prenda) -> prenda.validaParaTemperatura(climaGetter.temperatura(this.residencia.ciudad)))
        .collect(Collectors.toList());
        this.generarSugerenciasDesde(prendasAptas);
    }
}
public interface GeneradorSugerencias {
    private List<Sugerencia> generarSugerenciasDesde(List<Prenda> prendasAptas);
}


public class Prenda{

    public Boolean validaParaTemperatura(int temperatura){
        return temperatura <= this.temperaturaMaxima(); 
    }

    private int temperaturaMaxima(){
        return 20; // calculo 
    }


}

public class Sugerencia{
    Prenda superior;
    Prenda inferior;
    Prenda calzado;
}



public class Locacion{
    String ciudad;
    String pais;
    String direccion;
}


/////////////////Ejemplo testing/////////////////////////



public void testSugerenciaDia(){
    Usuario user = new Usuario(
        new Locacion("Argentina","Buenos Aires","Av. Rivadavia 1"),
        Arrays.asList(sombrero,saco,camisa,musculosa,zapatos)
    );
    Sugerencia sugerencia1=new Sugerencia(sombrero,camisa,zapatos);
    Sugerencia sugerencia2=new Sugerencia(sombrero,saco,zapatos);
    assertEquals(
        user.sugerenciasDiaPorTemperatura(new ClimaMock()),
        Arrays.asList(sugerencia1,sugerencia2)
    );
}
