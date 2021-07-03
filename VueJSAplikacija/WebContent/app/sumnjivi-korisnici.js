Vue.component("sumnjivi-korisnici", {
  data: function () {
    return {
      cookie: "",
      role: "",
      korisnici: [],
      ime: "",
      prezime: "",
      korisnicko: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
    this.loadKorisnici();
  },
  template: `
  
    <div>
    <admin-nav></admin-nav>
          <link rel="stylesheet" href="css/page_style.css" type="text/css">
	      <b-card id="page_content" style= "height: 100vh;
  overflow: hidden;
  overflow-y: scroll; 
  text-align: center;">

            <b-row >
                  <div class ="col-md-4" v-for="korisnik in korisnici">
                  <b-col>
                   <b-card
                      style="margin: 10px auto; max-width: 300px"
                      class="text-left"
                      bg-variant="light"
                  >
                    <b-card-text>
                    {{korisnik.name}} {{korisnik.surname}} <br>
                    Korisnicko ime: {{korisnik.username}} <br>
                    Lozinka: {{korisnik.password}} <br>
                    
                    <h5> Broj Otkazivanja: {{korisnik.numOfPenals}}</h5>

<b-button v-on:click="Blokiraj(korisnik)" variant="danger">Blokiraj</b-button>

                     </b-card>
                     
                    </b-col>
                    </div>
             </b-row>
    
        </b-card>
    </div>
      `,

  methods: {
    loadKorisnici() {
      axios
        .get(
          `rest/user/usersWithPenals`
        )
        .then((response) => {
          this.korisnici = response.data;
        });
    },
    Blokiraj(korisnik) {
      axios
        .get(
          `rest/user/blokiraj/name=${korisnik.name}`
        )
        .then((response) => {
			this.loadKorisnici();
        });
    },
  },
});
