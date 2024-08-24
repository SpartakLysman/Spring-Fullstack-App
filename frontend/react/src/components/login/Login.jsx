import {
    Alert,
    AlertIcon,
    Box,
    Button,
    Flex,
    FormLabel,
    Heading,
    Image,
    Input,
    Link,
    Stack,
    Text,
} from '@chakra-ui/react';
import {Formik, Form, useField} from "formik";
import * as Yup from 'yup';
import {useAuth} from "../context/AuthContext.jsx";
import {errorNotification} from "../../services/notification.js";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const LoginForm = () => {
    const { login } = useAuth();
    const navigate = useNavigate();

    return (
        <Formik
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    username: Yup.string()
                        .email("Must be valid email")
                        .required("Email is required"),
                    password: Yup.string()
                        .max(20, "Password cannot be more than 20 characters")
                        .required("Password is required")
                })
            }
            initialValues={{username: '', password: ''}}
            onSubmit={(values, {setSubmitting}) => {
                setSubmitting(true);
                login(values).then(res => {
                    navigate("/dashboard")
                    console.log("Successfully logged in");
                }).catch(err => {
                    errorNotification(
                        err.code,
                        err.response.data.message
                    )
                }).finally(() => {
                    setSubmitting(false);
                })
            }}>

            {({isValid, isSubmitting}) => (
                <Form>
                    <Stack mt={15} spacing={15}>
                        <MyTextInput
                            label={"Email"}
                            name={"username"}
                            type={"email"}
                            placeholder={"hellospartak@gmail.com"}
                        />
                        <MyTextInput
                            label={"Password"}
                            name={"password"}
                            type={"password"}
                            placeholder={"Type your password"}
                        />

                        <Button
                            bg={'blue.400'}
                            color={'white'}
                            _hover={{
                                bg: 'blue.500',
                            }}
                            type={"submit"}
                            disabled={!isValid || isSubmitting}>
                            Login
                        </Button>
                    </Stack>
                </Form>
            )}

        </Formik>
    )
}

const Login = () => {

    const { customer } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (customer) {
            navigate("/dashboard/customers");
        }
    })

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} m={200} alignItems={''} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Image
                        src={"https://raw.githubusercontent.com/SpartakLysman/Spring-Fullstack-App/main/logo.png"}
                        boxSize={"240px"}
                        alt={"Spartakuss Logo"}
                        borderRadius={"full"}
                        alignSelf={"center"}
                    />
                    <Heading fontSize={'2xl'} mb={0}>Sign in to your account</Heading>
                    <LoginForm/>
                    <Link color={"blue.500"} href={"/signup"}>
                        Don't have an account? Sign up now.
                    </Link>
                </Stack>
            </Flex>
            <Flex
                flexDirection={"column"}
                alignItems={"center"}
                justifyContent={"center"}
            >

                <Image
                    alt={'Login Image'}
                    objectFit={'fill'}
                    src={
                        'https://raw.githubusercontent.com/SpartakLysman/Spring-Fullstack-App/main/registration_image.png'
                    }
                    witdth={800}
                    height={1350}

                    // Adjust the size of the image
                />
                <Box
                    position="absolute"
                    top="82.5%"
                    left="82.5%"
                    transform="translate(-50%, -50%)"
                    color="white"
                    fontWeight="bold"
                    fontSize="3xl"
                    display="flex"
                    alignItems="center"
                    justifyContent="center"
                >
                    <Link href={"https://www.linkedin.com/in/spartak-lysman/"}>
                        <Text as='b' fontSize={"4xl"} color={"black"} fontWeight={"bold"}>
                            LinkedIn
                        </Text>
                    </Link>
                    <h1> 📄</h1>
                    <Box width="180px"/> {/* Space between the links */}
                    <Link href={"https://github.com/SpartakLysman"}>
                        <Text as='b' fontSize={"4xl"} color={"black"} fontWeight={"bold"}>
                            GitHu️b
                        </Text>
                    </Link>
                    <h1> 🛠️</h1>
                </Box>
            </Flex>
        </Stack>
    );
}

export default Login;