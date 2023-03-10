package com.mk.contactsmap.model

const val LAT_KEY = "latitude"
const val LNG_KEY = "longitude"
const val GPS_UPDATE_INTERVEL = 100L
val COUNTRY_CODES_ALL = listOf(
    CountryCode(country="Afghanistan",code="+93",iso="AF"),
    CountryCode(country="Albania",code="+355",iso="AL"),
    CountryCode(country="Algeria",code="+213",iso="DZ"),
    CountryCode(country="American Samoa",code="+684",iso="AS"),
    CountryCode(country="Andorra",code="+376",iso="AD"),
    CountryCode(country="Angola",code="+244",iso="AO"),
    CountryCode(country="Anguilla",code="+264",iso="AI"),
    CountryCode(country="Antarctica",code="+672",iso="AQ"),
    CountryCode(country="Antigua and Barbuda",code="+268",iso="AG"),
    CountryCode(country="Argentina",code="+54",iso="AR"),
    CountryCode(country="Armenia",code="+374",iso="AM"),
    CountryCode(country="Aruba",code="+297",iso="AW"),
    CountryCode(country="Australia",code="+61",iso="AU"),
    CountryCode(country="Austria",code="+43",iso="AT"),
    CountryCode(country="Azerbaijan",code="+994",iso="AZ"),
    CountryCode(country="Bahamas",code="+242",iso="BS"),
    CountryCode(country="Bahrain",code="+973",iso="BH"),
    CountryCode(country="Bangladesh",code="+880",iso="BD"),
    CountryCode(country="Barbados",code="+246",iso="BB"),
    CountryCode(country="Belarus",code="+375",iso="BY"),
    CountryCode(country="Belgium",code="+32",iso="BE"),
    CountryCode(country="Belize",code="+501",iso="BZ"),
    CountryCode(country="Benin",code="+229",iso="BJ"),
    CountryCode(country="Bermuda",code="+441",iso="BM"),
    CountryCode(country="Bhutan",code="+975",iso="BT"),
    CountryCode(country="Bolivia",code="+591",iso="BO"),
    CountryCode(country="Bosnia and Herzegovina",code="+387",iso="BA"),
    CountryCode(country="Botswana",code="+267",iso="BW"),
    CountryCode(country="Brazil",code="+55",iso="BR"),
    CountryCode(country="British Indian Ocean Territory",code="+246",iso="IO"),
    CountryCode(country="British Virgin Islands",code="+284",iso="VG"),
    CountryCode(country="Brunei",code="+673",iso="BN"),
    CountryCode(country="Bulgaria",code="+359",iso="BG"),
    CountryCode(country="Burkina Faso",code="+226",iso="BF"),
    CountryCode(country="Burundi",code="+257",iso="BI"),
    CountryCode(country="Cambodia",code="+855",iso="KH"),
    CountryCode(country="Cameroon",code="+237",iso="CM"),
    CountryCode(country="Canada",code="+1",iso="CA"),
    CountryCode(country="Cape Verde",code="+238",iso="CV"),
    CountryCode(country="Cayman Islands",code="+345",iso="KY"),
    CountryCode(country="Central African Republic",code="+236",iso="CF"),
    CountryCode(country="Chad",code="+235",iso="TD"),
    CountryCode(country="Chile",code="+56",iso="CL"),
    CountryCode(country="China",code="+86",iso="CN"),
    CountryCode(country="Christmas Island",code="+61",iso="CX"),
    CountryCode(country="Cocos Islands",code="+61",iso="CC"),
    CountryCode(country="Colombia",code="+57",iso="CO"),
    CountryCode(country="Comoros",code="+269",iso="KM"),
    CountryCode(country="Cook Islands",code="+682",iso="CK"),
    CountryCode(country="Costa Rica",code="+506",iso="CR"),
    CountryCode(country="Croatia",code="+385",iso="HR"),
    CountryCode(country="Cuba",code="+53",iso="CU"),
    CountryCode(country="Curacao",code="+599",iso="CW"),
    CountryCode(country="Cyprus",code="+357",iso="CY"),
    CountryCode(country="Czech Republic",code="+420",iso="CZ"),
    CountryCode(country="Democratic Republic of the Congo",code="+243",iso="CD"),
    CountryCode(country="Denmark",code="+45",iso="DK"),
    CountryCode(country="Djibouti",code="+253",iso="DJ"),
    CountryCode(country="Dominica",code="+767",iso="DM"),
    CountryCode(country="Dominican Republic",code="+809, 1-829, 1-849",iso="DO"),
    CountryCode(country="East Timor",code="+670",iso="TL"),
    CountryCode(country="Ecuador",code="+593",iso="EC"),
    CountryCode(country="Egypt",code="+20",iso="EG"),
    CountryCode(country="El Salvador",code="+503",iso="SV"),
    CountryCode(country="Equatorial Guinea",code="+240",iso="GQ"),
    CountryCode(country="Eritrea",code="+291",iso="ER"),
    CountryCode(country="Estonia",code="+372",iso="EE"),
    CountryCode(country="Ethiopia",code="+251",iso="ET"),
    CountryCode(country="Falkland Islands",code="+500",iso="FK"),
    CountryCode(country="Faroe Islands",code="+298",iso="FO"),
    CountryCode(country="Fiji",code="+679",iso="FJ"),
    CountryCode(country="Finland",code="+358",iso="FI"),
    CountryCode(country="France",code="+33",iso="FR"),
    CountryCode(country="French Polynesia",code="+689",iso="PF"),
    CountryCode(country="Gabon",code="+241",iso="GA"),
    CountryCode(country="Gambia",code="+220",iso="GM"),
    CountryCode(country="Georgia",code="+995",iso="GE"),
    CountryCode(country="Germany",code="+49",iso="DE"),
    CountryCode(country="Ghana",code="+233",iso="GH"),
    CountryCode(country="Gibraltar",code="+350",iso="GI"),
    CountryCode(country="Greece",code="+30",iso="GR"),
    CountryCode(country="Greenland",code="+299",iso="GL"),
    CountryCode(country="Grenada",code="+473",iso="GD"),
    CountryCode(country="Guam",code="+671",iso="GU"),
    CountryCode(country="Guatemala",code="+502",iso="GT"),
    CountryCode(country="Guernsey",code="+44-1481",iso="GG"),
    CountryCode(country="Guinea",code="+224",iso="GN"),
    CountryCode(country="Guinea-Bissau",code="+245",iso="GW"),
    CountryCode(country="Guyana",code="+592",iso="GY"),
    CountryCode(country="Haiti",code="+509",iso="HT"),
    CountryCode(country="Honduras",code="+504",iso="HN"),
    CountryCode(country="Hong Kong",code="+852",iso="HK"),
    CountryCode(country="Hungary",code="+36",iso="HU"),
    CountryCode(country="Iceland",code="+354",iso="IS"),
    CountryCode(country="India",code="+91",iso="IN"),
    CountryCode(country="Indonesia",code="+62",iso="ID"),
    CountryCode(country="Iran",code="+98",iso="IR"),
    CountryCode(country="Iraq",code="+964",iso="IQ"),
    CountryCode(country="Ireland",code="+353",iso="IE"),
    CountryCode(country="Isle of Man",code="+44-1624",iso="IM"),
    CountryCode(country="Israel",code="+972",iso="IL"),
    CountryCode(country="Italy",code="+39",iso="IT"),
    CountryCode(country="Ivory Coast",code="+225",iso="CI"),
    CountryCode(country="Jamaica",code="+876",iso="JM"),
    CountryCode(country="Japan",code="+81",iso="JP"),
    CountryCode(country="Jersey",code="+44-1534",iso="JE"),
    CountryCode(country="Jordan",code="+962",iso="JO"),
    CountryCode(country="Kazakhstan",code="+7",iso="KZ"),
    CountryCode(country="Kenya",code="+254",iso="KE"),
    CountryCode(country="Kiribati",code="+686",iso="KI"),
    CountryCode(country="Kosovo",code="+383",iso="XK"),
    CountryCode(country="Kuwait",code="+965",iso="KW"),
    CountryCode(country="Kyrgyzstan",code="+996",iso="KG"),
    CountryCode(country="Laos",code="+856",iso="LA"),
    CountryCode(country="Latvia",code="+371",iso="LV"),
    CountryCode(country="Lebanon",code="+961",iso="LB"),
    CountryCode(country="Lesotho",code="+266",iso="LS"),
    CountryCode(country="Liberia",code="+231",iso="LR"),
    CountryCode(country="Libya",code="+218",iso="LY"),
    CountryCode(country="Liechtenstein",code="+423",iso="LI"),
    CountryCode(country="Lithuania",code="+370",iso="LT"),
    CountryCode(country="Luxembourg",code="+352",iso="LU"),
    CountryCode(country="Macao",code="+853",iso="MO"),
    CountryCode(country="Macedonia",code="+389",iso="MK"),
    CountryCode(country="Madagascar",code="+261",iso="MG"),
    CountryCode(country="Malawi",code="+265",iso="MW"),
    CountryCode(country="Malaysia",code="+60",iso="MY"),
    CountryCode(country="Maldives",code="+960",iso="MV"),
    CountryCode(country="Mali",code="+223",iso="ML"),
    CountryCode(country="Malta",code="+356",iso="MT"),
    CountryCode(country="Marshall Islands",code="+692",iso="MH"),
    CountryCode(country="Mauritania",code="+222",iso="MR"),
    CountryCode(country="Mauritius",code="+230",iso="MU"),
    CountryCode(country="Mayotte",code="+262",iso="YT"),
    CountryCode(country="Mexico",code="+52",iso="MX"),
    CountryCode(country="Micronesia",code="+691",iso="FM"),
    CountryCode(country="Moldova",code="+373",iso="MD"),
    CountryCode(country="Monaco",code="+377",iso="MC"),
    CountryCode(country="Mongolia",code="+976",iso="MN"),
    CountryCode(country="Montenegro",code="+382",iso="ME"),
    CountryCode(country="Montserrat",code="+664",iso="MS"),
    CountryCode(country="Morocco",code="+212",iso="MA"),
    CountryCode(country="Mozambique",code="+258",iso="MZ"),
    CountryCode(country="Myanmar",code="+95",iso="MM"),
    CountryCode(country="Namibia",code="+264",iso="NA"),
    CountryCode(country="Nauru",code="+674",iso="NR"),
    CountryCode(country="Nepal",code="+977",iso="NP"),
    CountryCode(country="Netherlands",code="+31",iso="NL"),
    CountryCode(country="Netherlands Antilles",code="+599",iso="AN"),
    CountryCode(country="New Caledonia",code="+687",iso="NC"),
    CountryCode(country="New Zealand",code="+64",iso="NZ"),
    CountryCode(country="Nicaragua",code="+505",iso="NI"),
    CountryCode(country="Niger",code="+227",iso="NE"),
    CountryCode(country="Nigeria",code="+234",iso="NG"),
    CountryCode(country="Niue",code="+683",iso="NU"),
    CountryCode(country="North Korea",code="+850",iso="KP"),
    CountryCode(country="Northern Mariana Islands",code="+670",iso="MP"),
    CountryCode(country="Norway",code="+47",iso="NO"),
    CountryCode(country="Oman",code="+968",iso="OM"),
    CountryCode(country="Pakistan",code="+92",iso="PK"),
    CountryCode(country="Palau",code="+680",iso="PW"),
    CountryCode(country="Palestine",code="+970",iso="PS"),
    CountryCode(country="Panama",code="+507",iso="PA"),
    CountryCode(country="Papua New Guinea",code="+675",iso="PG"),
    CountryCode(country="Paraguay",code="+595",iso="PY"),
    CountryCode(country="Peru",code="+51",iso="PE"),
    CountryCode(country="Philippines",code="+63",iso="PH"),
    CountryCode(country="Pitcairn",code="+64",iso="PN"),
    CountryCode(country="Poland",code="+48",iso="PL"),
    CountryCode(country="Portugal",code="+351",iso="PT"),
    CountryCode(country="Puerto Rico",code="+787, 1-939",iso="PR"),
    CountryCode(country="Qatar",code="+974",iso="QA"),
    CountryCode(country="Republic of the Congo",code="+242",iso="CG"),
    CountryCode(country="Reunion",code="+262",iso="RE"),
    CountryCode(country="Romania",code="+40",iso="RO"),
    CountryCode(country="Russia",code="+7",iso="RU"),
    CountryCode(country="Rwanda",code="+250",iso="RW"),
    CountryCode(country="Saint Barthelemy",code="+590",iso="BL"),
    CountryCode(country="Saint Helena",code="+290",iso="SH"),
    CountryCode(country="Saint Kitts and Nevis",code="+869",iso="KN"),
    CountryCode(country="Saint Lucia",code="+758",iso="LC"),
    CountryCode(country="Saint Martin",code="+590",iso="MF"),
    CountryCode(country="Saint Pierre and Miquelon",code="+508",iso="PM"),
    CountryCode(country="Saint Vincent and the Grenadines",code="+784",iso="VC"),
    CountryCode(country="Samoa",code="+685",iso="WS"),
    CountryCode(country="San Marino",code="+378",iso="SM"),
    CountryCode(country="Sao Tome and Principe",code="+239",iso="ST"),
    CountryCode(country="Saudi Arabia",code="+966",iso="SA"),
    CountryCode(country="Senegal",code="+221",iso="SN"),
    CountryCode(country="Serbia",code="+381",iso="RS"),
    CountryCode(country="Seychelles",code="+248",iso="SC"),
    CountryCode(country="Sierra Leone",code="+232",iso="SL"),
    CountryCode(country="Singapore",code="+65",iso="SG"),
    CountryCode(country="Sint Maarten",code="+721",iso="SX"),
    CountryCode(country="Slovakia",code="+421",iso="SK"),
    CountryCode(country="Slovenia",code="+386",iso="SI"),
    CountryCode(country="Solomon Islands",code="+677",iso="SB"),
    CountryCode(country="Somalia",code="+252",iso="SO"),
    CountryCode(country="South Africa",code="+27",iso="ZA"),
    CountryCode(country="South Korea",code="+82",iso="KR"),
    CountryCode(country="South Sudan",code="+211",iso="SS"),
    CountryCode(country="Spain",code="+34",iso="ES"),
    CountryCode(country="Sri Lanka",code="+94",iso="LK"),
    CountryCode(country="Sudan",code="+249",iso="SD"),
    CountryCode(country="Suriname",code="+597",iso="SR"),
    CountryCode(country="Svalbard and Jan Mayen",code="+47",iso="SJ"),
    CountryCode(country="Swaziland",code="+268",iso="SZ"),
    CountryCode(country="Sweden",code="+46",iso="SE"),
    CountryCode(country="Switzerland",code="+41",iso="CH"),
    CountryCode(country="Syria",code="+963",iso="SY"),
    CountryCode(country="Taiwan",code="+886",iso="TW"),
    CountryCode(country="Tajikistan",code="+992",iso="TJ"),
    CountryCode(country="Tanzania",code="+255",iso="TZ"),
    CountryCode(country="Thailand",code="+66",iso="TH"),
    CountryCode(country="Togo",code="+228",iso="TG"),
    CountryCode(country="Tokelau",code="+690",iso="TK"),
    CountryCode(country="Tonga",code="+676",iso="TO"),
    CountryCode(country="Trinidad and Tobago",code="+868",iso="TT"),
    CountryCode(country="Tunisia",code="+216",iso="TN"),
    CountryCode(country="Turkey",code="+90",iso="TR"),
    CountryCode(country="Turkmenistan",code="+993",iso="TM"),
    CountryCode(country="Turks and Caicos Islands",code="+649",iso="TC"),
    CountryCode(country="Tuvalu",code="+688",iso="TV"),
    CountryCode(country="U.S. Virgin Islands",code="+340",iso="VI"),
    CountryCode(country="Uganda",code="+256",iso="UG"),
    CountryCode(country="Ukraine",code="+380",iso="UA"),
    CountryCode(country="United Arab Emirates",code="+971",iso="AE"),
    CountryCode(country="United Kingdom",code="+44",iso="GB"),
    CountryCode(country="United States",code="+1",iso="US"),
    CountryCode(country="Uruguay",code="+598",iso="UY"),
    CountryCode(country="Uzbekistan",code="+998",iso="UZ"),
    CountryCode(country="Vanuatu",code="+678",iso="VU"),
    CountryCode(country="Vatican",code="+379",iso="VA"),
    CountryCode(country="Venezuela",code="+58",iso="VE"),
    CountryCode(country="Vietnam",code="+84",iso="VN"),
    CountryCode(country="Wallis and Futuna",code="+681",iso="WF"),
    CountryCode(country="Western Sahara",code="+212",iso="EH"),
    CountryCode(country="Yemen",code="+967",iso="YE"),
    CountryCode(country="Zambia",code="+260",iso="ZM"),
    CountryCode(country="Zimbabwe",code="+263",iso="ZW")
)
val COUNTRY_CODES = listOf("+91", "+1","+355", "+213", "+684", "+376", "+244", "+264", "+672", "+268", "+54", "+374", "+297", "+61", "+43", "+994", "+242", "+973", "+880", "+246", "+375", "+32", "+501", "+229", "+441", "+975")
const val INDIA_TELEPHONE_CODE = "+91"