Vue.component("uredi-komentare", {
  data: function () {
    return {
      cookie: "",
      role: "",
      komentari: [],
      manifestacijaNaziv: "",
    };
  },
  mounted() {
    this.manifestacijaNaziv = localStorage.getItem("manifestacija");
    this.role = localStorage.getItem("role");
    if (this.role == null) {
      this.role = "default";
    }
    this.cookie = localStorage.getItem("cookie");
    if (this.cookie == null) {
      this.cookie = "";
    }
    this.loadKomentare();
  },
  template: `
    <div>
        <worker-nav></worker-nav>
            <link rel="stylesheet" href="css/home.css" type="text/css">
	      <b-card id="page_content" style= "height: 100vh;
                overflow: hidden;
                overflow-y: scroll; 
                text-align: center;">

                  <b-row >
                  <div class ="col-md-4" v-for="komentar in komentari">
                   <b-card
                      style="margin: 10px auto; max-width: 300px"
                        class="text-left"
                      bg-variant="light"
                  >
                    <b-card-text>
                    <h5 class="mt-0 mb-1">{{komentar.user}}</h5>
                        <p class="mb-0">
                            {{komentar.text}}
                        </p>
                                            <b-form-rating
                        id="rating-readonly"
                        v-model="komentar.grade"
                        no-border
                        readonly
                        variant="warning"
                    ></b-form-rating>
             		    </b-card-text>
						<div v-if="komentar.status === 'PENDING'">
						    <b-button  v-on:click="odobriKomentar(komentar)" variant="success">Odobri</b-button>
                      		<b-button  v-on:click="odbiKomentar(komentar)" variant="danger">Odbi</b-button>
						</div>

                       
                     </b-card>
                     
                    </div>
                  </b-row>
    </b-card>
    
    
    </div>

    `,
  methods: {
    odobriKomentar(komentar) {
      axios
        .get(
          `rest/comment/commentUpdate/manifestacija=${this.manifestacijaNaziv}/korisnik=${this.cookie}/grade=${komentar.grade}/text=${komentar.text}/status=APPROVED`
        )
        .then((response) => {
          this.loadKomentare();
        });
    },
    odbiKomentar(komentar) {
      axios
        .get(
          `rest/comment/commentUpdate/manifestacija=${this.manifestacijaNaziv}/korisnik=${this.cookie}/grade=${komentar.grade}/text=${komentar.text}/status=DENIED`
        )
        .then((response) => {
          this.loadKomentare();
        });
    },
    loadKomentare() {
      axios
        .get(
          `rest/comment/manifestationAll/manifestacija=${this.manifestacijaNaziv}`
        )
        .then((response) => {
          console.log(response.data);
          this.komentari = response.data;
        });
    },
  },
});
