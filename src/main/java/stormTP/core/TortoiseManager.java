package stormTP.core;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 Classe regroupant les fonctionnalités nécessaires au traitement des flux des coureurs
*/
public class TortoiseManager {
	
	public static final String CONST = "Constant";
	public static final String PROG = "En progression";
	public static final String REGR = "En régression";
	
	String nomsBinome ="";
	long dossard = -1;
	
	public TortoiseManager(long dossard, String nomsBinome){
		this.nomsBinome = nomsBinome;
		this.dossard = dossard;
	}
	
	
	/**
	 * Permet de filtrer les informations concernant votre coureur
	 * @param input : objet JSON contenant les observations de la course courante 
	 * @return un coureur
	 */
	public Runner filter(String input){
		
		Runner tortoise = null;
		try {
			JSONObject jobj = new JSONObject(input);
			
			JSONArray jarr = (JSONArray) jobj.get("tortoises");
			JSONObject jTortoise = (JSONObject) jarr.get( (int)(dossard));
			int top = jTortoise.getInt("top");
	        int position = jTortoise.getInt("position");
	        int nbDevant = jTortoise.getInt("nbDevant");
	        int nbDerriere = jTortoise.getInt("nbDerriere");
	        int total = jTortoise.getInt("total");
	        tortoise = new Runner(dossard, nomsBinome,nbDevant, nbDerriere, total, position, top);
		        
			
	       
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return tortoise;
				
				
	}
	
	/**
	 * Permet de calculer le rang de votre coureur
	 * @param id : identifiant du coureur
	 * @param top : numéro d'observation
	 * @param nom : nom du coureur
	 * @param nbDevant : nombre de coureur devant le coureur courant dans le classement
	 * @param nbDerriere : nombre de coureur derrière le coureur courant dans le classement
	 * @param total : nombre total de coureurs en lice
	 * @return un coureur
	 */
	public Runner computeRank(long id, long top, String nom, int nbDevant, int nbDerriere, int total){
		
		Runner tortoise = null;
		//@TODO
		String rank = String.valueOf(nbDevant + 1);
		int execo = nbDevant + nbDerriere + 1;
		if (execo < total)
			rank = rank + "ex";
		tortoise = new Runner(id, nom, nbDevant, nbDerriere, total, 0, top);
		tortoise.setRang(rank);
		return tortoise;
	}
	

	
	/**
	 * Permet de calculer les points bonus d'un coureur
	 * @param rang : rang du coureur
	 * @param total : nombre total de coureurs en lice
	 * @return nombre de points gagnés par le coureur
	 */
	public static int computePoints(String rang,  int total){
		int points = -1;
		int intRang =  Integer.parseInt(rang.replaceAll("ex", ""));		
        points = total - intRang ;

		return points;	
		
		
	}
	

	/**
	 * Permet de calculer la vitesse d'un coureur
	 * @param topInit : numéro d'observation initial
	 * @param topFin : numéro d'observation final
	 * @param posInit : position initiale du coureur sur la piste à l'observation initiale
	 * @param posFin : position finiale du coureur sur la piste à l'observation finale
	 * @return la vitesse du coueur (en nombre moyen de cellules par top d'observation) arrondie à 2 chiffres après la virgule
	 */
	public static double computeSpeed(long topInit, long topFin, int posInit, int posFin){
		
		double vitesse = -0.0;
		
		vitesse =(double)(posFin - posInit) / (double)(topFin - topInit);
		vitesse *= 100;
		vitesse = Math.floor(vitesse)/100;
		return vitesse;
		
	}
	
	
	/**
	 * Permet de calculer le rang moyen d'un coureur
	 * @param rangs : tableau des différentes valeurs de rang observées pour un coureur
	 * @return la moyenne des rangs
	 */
	public static int giveAverageRank(String[] rangs){
				
		
		int rang = 0;
		for (String stringRang : rangs) {
			rang += Integer.parseInt(stringRang.replace("ex", ""));
		}
		rang /= rangs.length;
		//@TODO
		
		return rang;
		
	}
	

	/**
	 * Permet de calculer l'évolution du rang moyen d'une tortue
	 * @param cavg : le rang moyen courant
	 * @param pavg : le rang moyen précédent
	 * @return la chaine de caractères correspondante à l'évolution du rang moyen 
	 */
	public static String giveRankEvolution(int cavg, int pavg){
				
		if (cavg - pavg == 0)
			return CONST;
		if (cavg - pavg < 0)
			return PROG;
		return REGR;
		
	}
	
	
	/**
	 * Permet de calculer le podium
	 * @param input : objet JSON correspondant aux observations de la course
	 * @return objet JSON correspondant au podium
	 */
	public static String getPodium(String input){
				
		JsonObjectBuilder r =  Json.createObjectBuilder();

		//@TODO
		
		
        
		return r.build().toString();
		
						
		
	}

	
	/**
	 * S'assure que le podium est complet et réaffecte le classement en fonction des ex aequo.
	 * @param tableau de coureurs classé par leur rang
	 * @return le podium sous forme de liste de liste de coureurs
	 */
	private static ArrayList<ArrayList<String>> computePodium(Runner[] runners){
		
		ArrayList<ArrayList<String>> realrank = new ArrayList<ArrayList<String>>();
		
		/* Initialisation des listes */
		for(int i = 0 ; i < runners.length; i++){
			realrank.add(i, new ArrayList<String>());
		}
		
		/* affectation des runner en fonction de leur position */
		for(int i = 0 ; i < runners.length; i++){
			realrank.get(runners[i].getNbDevant()).add(runners[i].getNom());
		}
		
		/* suppression des rangs vides dus aux ex aequo */
		int cpt = 0;
		int nbmaxiter = realrank.size();
		for(int i = 0 ; i < nbmaxiter; i++){
			if( realrank.get(cpt).size() == 0 ){
				realrank.remove(cpt);
			}else{
				cpt++;
			}
		}
		
		ArrayList<ArrayList<String>> marchesPodium = new ArrayList<ArrayList<String>>();
		
		/* transfert des données */
		for( int i = 0 ; i < Math.min(3,realrank.size())  ; i++){
			marchesPodium.add(i, realrank.get(i));
		}
		
		return marchesPodium;
		
	}
	
	
	
	

	
	
	

}
