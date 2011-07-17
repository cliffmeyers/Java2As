package flexserverlib.java2as.core.conf.packages;

import flexserverlib.java2as.core.conf.PackageMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public class RuleBasedPackageMapper implements PackageMapper {

	//
	// Fields
	//

	private List<PackageMapperRule> mapperRules;

	//
	// Constructors
	//

	public RuleBasedPackageMapper() {
		mapperRules = new ArrayList<PackageMapperRule>();
	}

	//
	// Public Methods
	//

	public boolean canMap(String packageName) {
		PackageMapper lastMatch = null;
		for (PackageMapper mapper : mapperRules) {
			if (mapper.canMap(packageName))
				lastMatch = mapper;
		}
		return lastMatch != null;
	}

	public String performMap(String packageName) {
		PackageMapper lastMatch = null;
		for (PackageMapper mapper : mapperRules) {
			if (mapper.canMap(packageName))
				lastMatch = mapper;
		}
		return lastMatch.performMap(packageName);
	}

	public void addMapperRule(PackageMapperRule mapperRule) {
		mapperRules.add(mapperRule);
		// keep mapperRules sorted alphabetically so we can map just by looping through the list
		Collections.sort(mapperRules);
	}

	//
	// Getters and Setters
	//

	public void setMapperRules(List<PackageMapperRule> mapperRules) {
		this.mapperRules.addAll(mapperRules);
		// keep mapperRules sorted alphabetically so we can map just by looping through the list
		Collections.sort(this.mapperRules);
	}

}
