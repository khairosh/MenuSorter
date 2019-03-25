# MenuSorter
NES-н цэсийг хэрэглээгээр нь эрэмбэлнэ

## Соорс код
**src/bilguun**: Билгүүнийх

**src/eegii**: Ээгийгийнх

**src/khairosh**: Хайрошийнх

## Тест өгөгдөл
**data** фолдерт байгаа.

## Ажиллуулах

**src/common/MenuSorter.java** классыг run хийнэ.

## Гүйцэтгэл

**menu.json** файлыг **100000** удаа давтаж ажиллуулахад:
- Ээгий: 9231 ms
- Билгүүн: 1408 ms
- Хайрош: 218 ms

тус тус зарцуулав. Үр дүн бүгд зөв.

**menu2.json** файл дээр Ээгийгийнх бага зэрэг зөрүүтэй үр дүн буцааж, бусад хоёр нь зөв ажиллав. 

### menu2.json тестийн хариу
**Билгүүн**
```
[{
	"title": "Configuration Management",
	"items": [{
		"title": "Бусад",
		"items": [{
			"title": "Апдэйт",
			"items": [{
				"code": "CFGM-B-170"
			}, {
				"code": "CFGM-B-130"
			}]
		}, {
			"code": "CFGM-B-200"
		}, {
			"code": "CFGM-B-210"
		}, {
			"code": "CFGM-B-180"
		}]
	}, {
		"title": "Тухай",
		"items": [{
			"code": "CFGM-B-170"
		}, {
			"code": "CFGM-B-900"
		}]
	}, {
		"code": "CFGM-B-130"
	}, {
		"code": "CFGM-B-100"
	}, {
		"code": "CFGM-B-120"
	}]
}]
```

**Хайрош**
```
[{
	"title": "Configuration Management",
	"items": [{
		"title": "Бусад",
		"items": [{
			"title": "Апдэйт",
			"items": [{
				"code": "CFGM-B-170"
			}, {
				"code": "CFGM-B-130"
			}]
		}, {
			"code": "CFGM-B-200"
		}, {
			"code": "CFGM-B-210"
		}, {
			"code": "CFGM-B-180"
		}]
	}, {
		"title": "Тухай",
		"items": [{
			"code": "CFGM-B-170"
		}, {
			"code": "CFGM-B-900"
		}]
	}, {
		"code": "CFGM-B-130"
	}, {
		"code": "CFGM-B-100"
	}, {
		"code": "CFGM-B-120"
	}]
}]
```

**Ээгий**
```
[{
	"title": "Configuration Management",
	"items": [{
		"title": "Тухай",
		"items": [{
			"code": "CFGM-B-170"
		}, {
			"code": "CFGM-B-900"
		}]
	}, {
		"code": "CFGM-B-130"
	}, {
		"code": "CFGM-B-100"
	}, {
		"title": "Бусад",
		"items": [{
			"code": "CFGM-B-180"
		}, {
			"code": "CFGM-B-200"
		}, {
			"code": "CFGM-B-210"
		}, {
			"title": "Апдэйт",
			"items": [{
				"code": "CFGM-B-130"
			}, {
				"code": "CFGM-B-170"
			}]
		}]
	}, {
		"code": "CFGM-B-120"
	}]
}]
```