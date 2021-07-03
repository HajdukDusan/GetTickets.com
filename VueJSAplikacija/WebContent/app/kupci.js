Vue.component("kupci", {
  data: function () {
    return {
      cookie: "",
      role: "",
      kupci: [],
      manifestacijaNaziv: "",
    };
  },
  mounted() {
    this.manifestacijaNaziv = localStorage.getItem("manifestacija");
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
    this.loadKupce();
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
                  <div class ="col-md-4" v-for="kupac in kupci">
                   <b-card
                      style="margin: 10px auto; max-width: 300px"
                        class="text-left"
                      bg-variant="light"
                  >
                    <b-card-text>
                    <h5 class="mt-0 mb-1">{{kupac.username}}</h5>
             		</b-card-text>

                       
                     </b-card>
                     
                    </div>
                  </b-row>
    </b-card>
    
    
    </div>

    `,
  methods: {
    loadKupce() {
      axios
        .get(
          `rest/manifestation/allBuyers/manifestation=${this.manifestacijaNaziv}`
        )
        .then((response) => {
          this.kupci = response.data;
        });
    },
  },
});
